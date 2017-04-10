package cn.blmdz.hunt.engine.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.config.model.BaseConfig;
import cn.blmdz.hunt.engine.config.model.I18nConfig;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.utils.FileLoader;
import cn.blmdz.hunt.engine.utils.FileLoaderHelper;
import cn.blmdz.hunt.engine.utils.Yamls;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Component
public class I18nResourceBundle implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(I18nResourceBundle.class);
	@Autowired
	private FileLoaderHelper fileLoaderHelper;
	@Autowired
	private Setting setting;
	private LoadingCache<App, Optional<List<LocaleTag>>> localesCache;
	private LoadingCache<ResourceKey, Optional<I18nResource>> resourceCache;
	private final ConcurrentMap<String, BaseConfig> i18nConfigMap = Maps.newConcurrentMap();
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private static final List<LocaleTag> DEFAULT_LOCALES = Lists
			.newArrayList(new LocaleTag[] { new LocaleTag("zh_CN") });

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		this.localesCache = CacheBuilder.newBuilder().build(new CacheLoader<App, Optional<List<LocaleTag>>>() {
			@Override
			public Optional<List<LocaleTag>> load(App app) throws Exception {
				String path = I18nResourceBundle.this.getLocalesFilePath(app);
				FileLoader.Resp resp = I18nResourceBundle.this.fileLoaderHelper.load(path);
				if (resp.isNotFound()) {
					I18nResourceBundle.log.warn(
							"resource locales file not found, use zh_CN for default. app: {}, path: {}", app.getKey(),
							path);
					return Optional.of(I18nResourceBundle.DEFAULT_LOCALES);
				} else {
					List<String> yamlResult = Yamls.getInstance().loadAs(resp.asString(), ArrayList.class);
					List<LocaleTag> locales = Lists.newArrayList();

					for (String localeStr : yamlResult) {
						locales.add(new LocaleTag(localeStr));
					}

					return Optional.of(locales);
				}
			}
		});
		this.resourceCache = CacheBuilder.newBuilder()
				.build(new CacheLoader<ResourceKey, Optional<I18nResource>>() {
					@Override
					public Optional<I18nResource> load(ResourceKey key) throws Exception {
						App app = key.getApp();
//						String baseName = key.getBaseName();
						Locale locale = key.getLocale();
						String path = key.getResourceFilePath();
						FileLoader.Resp resp = I18nResourceBundle.this.fileLoaderHelper.load(path);
						BaseConfig config = new I18nConfig();
						config.setLocation(path);
						config.setSign(resp.getSign());
						config.setLoadedAt(DateTime.now().toDate());
						I18nResourceBundle.this.i18nConfigMap.put(path, config);
						if (resp.isNotFound()) {
							I18nResourceBundle.log.error("resource file not found, app: {}, path: {}", app.getKey(),
									path);
							return Optional.absent();
						} else {
							Map<String, Object> yamlResult = Yamls.getInstance().loadAs(resp.asString(), HashMap.class);
							return Optional.of(new I18nResource(yamlResult, locale));
						}
					}
				});
		if (this.setting.isDevMode()) {
			this.executorService.scheduleAtFixedRate(this, 5L, 5L, TimeUnit.SECONDS);
		} else {
			this.executorService.scheduleAtFixedRate(this, 5L, 5L, TimeUnit.MINUTES);
		}

	}

	public I18nResource getResource(App app, String baseName, Locale locale) {
		ResourceKey resourceKey = new ResourceKey(app, baseName, locale);
		return resourceCache.getUnchecked(resourceKey).orNull();
	}

	public Locale firstMatchLocale(App app, List<Locale> requestLocales) {
		List<LocaleTag> locales = localesCache.getUnchecked(app).orNull();
		if (locales != null && !locales.isEmpty()) {
			for (Locale locale : requestLocales) {
				LocaleTag matched = Locales.bestMatch(locale, locales);
				if (matched != null) {
					return matched.toLocale();
				}
			}

			return ((LocaleTag) locales.get(0)).toLocale();
		} else {
			return null;
		}
	}

	public void clearAllCache() {
		this.localesCache.invalidateAll();
		this.resourceCache.invalidateAll();
	}

	private String getLocalesFilePath(App app) {
		return app.getAssetsHome() + "resources/locales.yaml";
	}
	
	@Override
	public void run() {
		this.checkIfNeedReload();
	}

	private void checkIfNeedReload() {
		for (String path : this.i18nConfigMap.keySet()) {
			BaseConfig config = (BaseConfig) this.i18nConfigMap.get(path);
			FileLoader.Resp resp = this.fileLoaderHelper.load(path, config.getSign());
			if (resp.modified()) {
				log.info("reload i18 resource cause file has been changed {}", path);
				config.setLocation(path);
				config.setSign(resp.getSign());
				config.setLoadedAt(DateTime.now().toDate());
				this.resourceCache.invalidateAll();
				break;
			}
		}

	}

	@Getter
	@EqualsAndHashCode
	@AllArgsConstructor
	public static class ResourceKey {
		private App app;
		private String baseName;
		private Locale locale;

		public String getResourceFilePath() {
			return this.app.getAssetsHome() + "resources/" + this.baseName + "/" + this.locale.toString() + ".yaml";
		}
	}
}
