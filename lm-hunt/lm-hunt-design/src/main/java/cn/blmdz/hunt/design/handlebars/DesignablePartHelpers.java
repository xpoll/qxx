package cn.blmdz.hunt.design.handlebars;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.common.model.BaseUser;
import cn.blmdz.hunt.common.util.Splitters;
import cn.blmdz.hunt.common.util.UserUtil;
import cn.blmdz.hunt.design.service.PagePartService;
import cn.blmdz.hunt.design.util.PagePartPath;
import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;

@Component
public class DesignablePartHelpers {
	private static final String PARTS_THIS_KEY = "THIS";
	@Autowired
	private HandlebarsEngine handlebarsEngine;
	@Autowired
	private PagePartService pagePartService;

	@PostConstruct
	public void init() {
		handlebarsEngine.registerHelper("designable", new Helper<Object>() {
			@Override
			public CharSequence apply(Object useless, Options options) throws IOException {
				String scope = options.hash("scope");
				Object key = options.hash("key");
				String path = options.hash.containsKey("path") ? (String) options
						.hash("path") : (String) options.get(RenderConstants.PATH);
				PagePartPath pagePartPath = new PagePartPath(scope, key, path);
				path = pagePartPath.toString();
				boolean isDesignMode = options.get(RenderConstants.DESIGN_MODE) != null;
				Map<String, String> parts;
				if (isDesignMode) {
					parts = pagePartService.findByKey(ThreadVars.getAppKey(), path);
				} else {
					parts = pagePartService.findReleaseByKey(ThreadVars.getAppKey(), path);
				}

				Map<String, Map<String, String>> allParts = Maps.newHashMap();
				allParts.put(PARTS_THIS_KEY, parts);
				options.data(RenderConstants.PARTS, allParts);
				return new SafeString(
						"<div id=\"eve-designable\" style=\"display:none;\" data-path=\""
								+ path + "\"></div>");
			}
		});
		handlebarsEngine.registerHelper("designPart", new Helper<String>() {
			@Override
			public CharSequence apply(String key, Options options) throws IOException {
				boolean isDesignMode = options.get(RenderConstants.DESIGN_MODE) != null;
				boolean isEditTemplate = options.get(RenderConstants.EDIT_TEMPLATE) != null;
				boolean hasAuth = true;
				if (isDesignMode && options.hash.containsKey("rule")) {
					List<String> rules = Splitters.COMMA
							.splitToList((CharSequence) options.hash("rule"));
					BaseUser currentUser = UserUtil.getCurrentUser();
					String userTypeStr = currentUser.getType() == null ? ""
							: currentUser.getType().toString();
					if (!rules.contains(userTypeStr)) {
						hasAuth = false;
					}
				}

				String partsKey = PARTS_THIS_KEY;
				Boolean isTemplate = options.hash("template", Boolean.valueOf(false));
				Map<String, Map<String, String>> allParts;
				if (isEditTemplate && isTemplate.booleanValue()) {
					allParts = Maps.newHashMap();
					Map<String, String> templateMap = Maps.newHashMap();
					templateMap.put(key, (String) options.get(RenderConstants.TEMPLATE_CONTENT));
					allParts.put(partsKey, templateMap);
				} else {
					allParts = options.get(RenderConstants.PARTS);
					if (allParts == null) {
						allParts = Maps.newHashMap();
						options.data(RenderConstants.PARTS, allParts);
					}

					String scope = (String) options.hash("scope");
					if (!Strings.isNullOrEmpty(scope)) {
						partsKey = (new PagePartPath(scope, options
								.hash("key"), (String) options
								.hash("path"))).toString();
						if (!allParts.containsKey(partsKey)) {
							Map<String, String> parts;
							if (isDesignMode) {
								parts = pagePartService
										.findByKey(
												ThreadVars.getAppKey(),
												partsKey);
							} else {
								parts = pagePartService
										.findReleaseByKey(
												ThreadVars.getAppKey(),
												partsKey);
							}

							allParts.put(partsKey, parts);
						}
					}
				}

				boolean canDesigned = !isTemplate.booleanValue()
						|| isEditTemplate;
				StringBuilder sb = new StringBuilder();
				if (isDesignMode) {
					if (hasAuth && canDesigned) {
						sb.append("<div class=\"eve-design-part");
						Boolean isEditArea = (Boolean) options.hash(
								"isEditArea", Boolean.valueOf(false));
						if (isEditArea.booleanValue()) {
							sb.append(" eve-part");
						}

						sb.append("\"");
						sb.append(" data-part-key=\"").append(key)
								.append("\"");
					} else {
						sb.append("<div data-part-key=\"").append(key)
								.append("\"");
					}

					String scope = (String) options.hash("scope");
					if (!Strings.isNullOrEmpty(scope)) {
						sb.append(" data-part-scope=\"").append(scope)
								.append("\"");
					}

					if (!Objects.equal(partsKey, PARTS_THIS_KEY)) {
						sb.append(" data-parts-key=\"")
								.append(partsKey).append("\"");
					}

					String desc = (String) options.hash("desc");
					if (!Strings.isNullOrEmpty(desc)) {
						sb.append(" data-desc=\"").append(desc)
								.append("\"");
					}

					sb.append(">");
				}

				Map<String, String> realParts = allParts.get(partsKey);
				if (realParts != null
						&& !Strings.isNullOrEmpty((String) realParts
								.get(key))) {
					String partTemplateStr = (String) realParts
							.get(key);
					sb.append(handlebarsEngine
							.execInline(partTemplateStr,
									options.context));
				} else {
					Boolean isHide = options.hash("hide", Boolean.valueOf(false));
					if (hasAuth && canDesigned) {
						sb.append("<div class=\"eve-grid-auto eve-design-part");
						if (isHide.booleanValue()) {
							sb.append(" eve-hide");
						}
					} else {
						sb.append("<div class=\"");
						if (isHide.booleanValue()) {
							sb.append("eve-hide");
						}
					}

					sb.append("\"");
					String style = (String) options.hash("style");
					if (!Strings.isNullOrEmpty(style)) {
						sb.append(" style=\"").append(style)
								.append("\"");
					}

					sb.append(">");
					sb.append((CharSequence) (options.tagType == TagType.SECTION ? options
							.fn() : ""));
					sb.append("</div>");
				}

				if (isDesignMode) {
					sb.append("</div>");
				}

				return new SafeString(sb);
			}
		});
	}
}
