package cn.blmdz.hunt.engine.handlebars;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.HandlebarsException;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.common.exception.UserNotLoginException;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.mapping.Invoker;
import cn.blmdz.hunt.engine.service.ConfigService;
import cn.blmdz.hunt.engine.utils.FileLoaderHelper;
import lombok.Getter;
import lombok.Setter;

@Component
public class HandlebarsEngine implements ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(HandlebarsEngine.class);
	@Getter
	@Setter
	private Handlebars handlebars;
	@Getter
	@Setter
	private Invoker invoker;
	@Getter
	private final LoadingCache<String, Optional<Template>> caches;
	@Getter
	@Setter
	private ConfigService configService;
	@Getter
	@Setter
	private List<ContextHandler> handlers;
	@Getter
	@Setter
	private ApplicationContext context;

	@Autowired(required = false)
	public HandlebarsEngine(Invoker invoker, Setting setting, ConfigService configService,
			FileLoaderHelper fileLoaderHelper) {
		this.invoker = invoker;
		TemplateLoader templateLoader = new GreatTemplateLoader(fileLoaderHelper);
		this.handlebars = new Handlebars(templateLoader);
		this.caches = this.initCache(!setting.isDevMode());
		this.configService = configService;
	}

	@PostConstruct
	public void init() {
		Map<String, ContextHandler> contextHandlers = this.context.getBeansOfType(ContextHandler.class);
		if (contextHandlers != null && !contextHandlers.isEmpty()) {
			this.handlers = Lists.newArrayListWithCapacity(contextHandlers.size());

			for (Entry<String, ContextHandler> entry : contextHandlers.entrySet()) {
				this.handlers.add(entry.getValue());
			}
		} else {
			this.handlers = Collections.emptyList();
		}

	}

	private LoadingCache<String, Optional<Template>> initCache(boolean buildCache) {
		return buildCache ? CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Optional<Template>>() {
					public Optional<Template> load(String path) throws Exception {
						Template t = null;
						String realPath = path.split("]")[1];

						try {
							t = HandlebarsEngine.this.handlebars.compile(realPath);
						} catch (Exception var5) {
							HandlebarsEngine.log.error("failed to compile template(path={}), cause:{}", path,
									var5.getMessage());
						}

						return Optional.fromNullable(t);
					}
				}) : null;
	}

	public void registerHelper(String name, Helper<?> helper) {
		this.handlebars.registerHelper(name, helper);
	}

	public void registerHelpers(Map<String, Helper<?>> helpers) {
		for (String key : helpers.keySet()) {
			this.handlebars.registerHelper(key, helpers.get(key));
		}

	}

	public String execInline(String templateStr, Object context) {
		return this.execInline(templateStr, context, (String) null);
	}

	public String execInline(String templateStr, Object params, String cacheKey) {
		try {
			if (params == null) {
				params = Maps.newHashMap();
			}

			Template template;
			if (this.caches != null && cacheKey != null) {
				template = this.caches.getUnchecked("inline/" + cacheKey).orNull();
			} else {
				template = this.handlebars.compileInline(templateStr);
			}

			if (template == null) {
				log.error("failed to exec handlebars\' template:{}", templateStr);
				return "";
			} else {
				return template.apply(params);
			}
		} catch (Exception var5) {
			log.error("exec handlebars\' template failed: {},cause:{}", templateStr,
					Throwables.getStackTraceAsString(var5));
			return "";
		}
	}

	public String execPath(String path, Map<String, Object> params, boolean isComponent) throws FileNotFoundException {
		try {
			if (params == null) {
				params = Maps.newHashMap();
			}

			Template template;
			if (isComponent) {
				String componentViewPath = "component:" + path + "/view";
				if (this.caches == null) {
					template = this.handlebars.compile(componentViewPath);
				} else {
					template = this.caches.getUnchecked(this.getAppPathKey(componentViewPath)).orNull();
				}

				if (template == null) {
					log.error("failed to exec handlebars template:path={}", path);
					return "";
				}

				params.put("_COMP_PATH_", path);
			} else {
				if (this.caches == null) {
					template = this.handlebars.compile(path);
				} else {
					template = this.caches.getUnchecked(this.getAppPathKey(path)).orNull();
				}

				if (template == null) {
					throw new FileNotFoundException("view not found: " + path);
				}
			}

			params.put("_USER_", UserUtil.getCurrentUser());
			params.put("_HREF_", this.configService.getFrontConfig(ThreadVars.getAppKey()).getCurrentHrefs(ThreadVars.getHost()));
			params.put("_LOCALE_", ThreadVars.getLocale().toString());
			return template.apply(params);
		} catch (Exception var6) {
			Throwables.propagateIfInstanceOf(var6, FileNotFoundException.class);
			if (var6 instanceof HandlebarsException) {
				Throwables.propagateIfInstanceOf(var6.getCause(), FileNotFoundException.class);
				Throwables.propagateIfInstanceOf(var6.getCause(), UserNotLoginException.class);
			}

			log.error("failed to execute handlebars\' template(path={}),cause:{} ", path,
					Throwables.getStackTraceAsString(var6));
			return "";
		}
	}

	public String execComponent(cn.blmdz.hunt.engine.config.model.Component component, Map<String, Object> context) {
		Map<String, String> services = component.getServices() == null ? new HashMap<String, String>() : component.getServices();
		if (!Strings.isNullOrEmpty(component.getService())) {
			services.put("_DATA_", component.getService());
		}

		if (!services.isEmpty()) {
			Map<String, Object> invokeResults = Maps.newHashMap();

			try {
				for (String key : services.keySet()) {
					Object result = this.invoker.invoke((String) services.get(key), context);
					invokeResults.put(key, result);
				}
			} catch (UserNotLoginException var9) {
				log.error("user doesn\'t login when invoke component, component:{}", component);
				if (context.get("_DESIGN_MODE_") == null) {
					throw var9;
				}
			} catch (Exception var10) {
				log.error("error when invoke component, component: {}", component, var10);
				context.put("_ERROR_", var10.getMessage());
			}

			context.putAll(invokeResults);
		}

		try {
			if (this.handlers != null && !this.handlers.isEmpty()) {
				for (ContextHandler handler : this.handlers) {
					handler.handle(component, context);
				}
			}

			return this.execPath(component.getPath(), context, true);
		} catch (Exception var8) {
			log.error("failed to execute handlebars\' template(path={}),cause:{} ", component.getPath(),
					Throwables.getStackTraceAsString(var8));
			return "";
		}
	}

	private String getAppPathKey(String path) {
		return "[" + ThreadVars.getAppKey() + "]" + path;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}