package cn.blmdz.hunt.engine.handlebars;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.html.HtmlEscapers;

import cn.blmdz.hunt.common.util.JsonMapper;
import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.config.ConfigManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RenderHelpers extends AbstractHelpers {
	@Autowired
	private HandlebarsEngine handlebarsEngine;
	@Autowired
	private ConfigManager configManager;
	@Autowired
	private Setting setting;

	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("inject", new Helper<String>() {
			@Override
			@SuppressWarnings("unchecked")
			public CharSequence apply(String compPath, Options options) throws IOException {
				boolean isDesignMode = options.get("_DESIGN_MODE_") != null;
				Map<String, Object> tempContext = Maps.newHashMap();
				if (options.context.model() instanceof Map) {
					tempContext.putAll((Map<String, Object>) options.context.model());
					if (RenderHelpers.this.setting.isClearInjectNestedContext()) {
						Set<String> cdataKeys = (Set<String>) tempContext.remove("_CDATA_KEYS_");
						if (cdataKeys != null) {
							for (String key : cdataKeys) {
								tempContext.remove(key);
							}
						}
					}

					if (isDesignMode) {
						tempContext.remove("_COMP_DATA_");
					}
				}

				Set<String> cdataKeys = Sets.newHashSet();
				if (options.hash != null && !options.hash.isEmpty()) {
					tempContext.putAll(options.hash);
					cdataKeys.addAll(options.hash.keySet());
				}

				if (options.tagType == TagType.SECTION && StringUtils.isNotBlank(options.fn.text())) {
					Map<String, Object> config = JsonMapper.nonEmptyMapper().fromJson(String.valueOf(options.fn()), Map.class);
					if (config != null && !config.isEmpty()) {
						cdataKeys.addAll(config.keySet());
						tempContext.putAll(config);
						if (isDesignMode) {
							tempContext.put("_COMP_DATA_", HtmlEscapers.htmlEscaper().escape(options.fn.text().trim()));
						}
					}
				}

				if (RenderHelpers.this.setting.isClearInjectNestedContext()) {
					tempContext.put("_CDATA_KEYS_", cdataKeys);
				}

				cn.blmdz.hunt.engine.config.model.Component component = new cn.blmdz.hunt.engine.config.model.Component();
				component.setPath(compPath);
				Object firstParam = options.param(0, Boolean.valueOf(true));
				if (firstParam.equals(Boolean.valueOf(true))) {
					cn.blmdz.hunt.engine.config.model.Component result = RenderHelpers.this.configManager
							.findComponent(ThreadVars.getAppKey(), compPath);
					if (result == null) {
						RenderHelpers.log.debug("can\'t find component config for path:{}", compPath);
					} else {
						component = result;
					}
				} else if (firstParam instanceof String) {
					component.setService((String) firstParam);
				}

				if (isDesignMode) {
					String[] paths = compPath.split("/");
					tempContext.put("_COMP_NAME_", MoreObjects.firstNonNull(component.getName(), paths[paths.length - 1]));
				}

				return new SafeString(RenderHelpers.this.handlebarsEngine.execComponent(component, tempContext));
			}
		});
		helpers.put("component", new Helper<String>() {
			@Override
			public CharSequence apply(String className, Options options) throws IOException {
				boolean isDesignMode = options.get("_DESIGN_MODE_") != null;
				className = className + " eve-component";
				Object customClassName = options.context.get("_CLASS_");
				StringBuilder compOpenTag = (new StringBuilder("<div class=\"")).append(className);
				if (customClassName != null) {
					compOpenTag.append(" ").append(customClassName);
				}

				compOpenTag.append("\"");
				Object style = options.context.get("_STYLE_");
				if (style != null) {
					compOpenTag.append(" style=\"").append(style).append("\"");
				}

				Object compName = options.context.get("_COMP_NAME_");
				if (compName != null) {
					compOpenTag.append(" data-comp-name=\"").append(compName).append("\"");
				}

				Object compData = options.context.get("_COMP_DATA_");
				if (compData != null) {
					compOpenTag.append(" data-comp-data=\"").append(compData).append("\"");
				}

				Object compPath = options.context.get("_COMP_PATH_");
				if (compPath != null) {
					compOpenTag.append(" data-comp-path=\"").append(compPath).append("\"");
				}

				if (isDesignMode) {
					compOpenTag.append(" data-comp-class=\"").append(className).append("\"");
				}

				compOpenTag.append(" >");
				return new SafeString(compOpenTag.toString() + options.fn() + "</div>");
			}
		});
	}
}
