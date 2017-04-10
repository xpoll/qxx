package cn.blmdz.hunt.engine.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.handlebars.AbstractHelpers;

@Component
public class I18nHelpers extends AbstractHelpers {
	private static final Pattern PATTERN = Pattern.compile("\\{(\\d+)\\}");
	private static final String DEFAULT_BUNDLE = "messages";
	// @Autowired
	// private HandlebarsEngine handlebarsEngine;
	@Autowired
	private I18nResourceBundle i18nResourceBundle;

	@Override
	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("i18n", new Helper<String>() {
			@Override
			public CharSequence apply(String key, Options options) throws IOException {
				Validate.notEmpty(key, "found: \'%s\', expected \'bundle\'s key\'", new Object[] { key });
				String hashLocale = (String) options.hash("locale");
				Locale locale = hashLocale == null ? ThreadVars.getLocale() : StringUtils.parseLocaleString(hashLocale);
				String baseName = (String) options.hash("bundle", DEFAULT_BUNDLE);
				I18nResource i18nResource = I18nHelpers.this.i18nResourceBundle.getResource(ThreadVars.getApp(),
						baseName, locale);
				return i18nResource == null ? key : i18nResource.message(key, options.params);
			}
		});
		helpers.put("i18nJs", new Helper<Object>() {
			@Override
			public CharSequence apply(Object useless, Options options) throws IOException {
				String hashLocale = (String) options.hash("locale");
				Locale locale = hashLocale == null ? ThreadVars.getLocale() : StringUtils.parseLocaleString(hashLocale);
				String baseName = (String) options.hash("bundle", DEFAULT_BUNDLE);
				I18nResource i18nResource = I18nHelpers.this.i18nResourceBundle.getResource(ThreadVars.getApp(),
						baseName, locale);
				if (i18nResource == null) {
					return new SafeString("// Error: No i18n resource found !!");
				} else {
					StringBuilder buffer = new StringBuilder();
					Boolean wrap = (Boolean) options.hash("wrap", Boolean.valueOf(false));
					if (wrap.booleanValue()) {
						buffer.append("<script type=\'text/javascript\'>\n");
					}

					buffer.append("  // ").append(locale.getDisplayName()).append("\n");
					buffer.append("  I18n = window.I18n || {};\n");
					buffer.append("  I18n.defaultLocale = \'").append(locale.toString()).append("\';\n");
					buffer.append("  I18n.locale = \'").append(locale.toString()).append("\';\n");
					buffer.append("  I18n.translations = I18n.translations || {};\n");
					buffer.append("  I18n.translations[\'").append(locale.toString()).append("\'] = {\n");
					StringBuilder body = new StringBuilder();
					String separator = ",\n";

					for (String key : i18nResource.keys()) {
						String message = I18nHelpers.this.message(i18nResource.message(key, new Object[0]));
						body.append("    \"").append(key).append("\": ");
						body.append("\"").append(message).append("\"").append(separator);
					}

					if (body.length() > 0) {
						body.setLength(body.length() - separator.length());
						buffer.append(body);
					}

					buffer.append("\n  };\n");
					if (wrap.booleanValue()) {
						buffer.append("</script>\n");
					}

					return new SafeString(buffer);
				}
			}
		});
		helpers.put("i18nJsHelper", new Helper<Object>() {
			@Override
			public CharSequence apply(Object useless, Options options) throws IOException {
				String js = "if (window.Handlebars) {\n  Handlebars.registerHelper(\"i18n\", function(key) {\n    if (arguments.length == 1) {\n      return;\n    }\n    // remove key and options from arguments\n    var argArray = Array.prototype.slice.call(arguments, 1, -1);\n    var argMap = _.indexBy(argArray, function(uselessValue, index) {\n      return \"arg\" + index;\n    });\n    return I18n.t(key, argMap);\n  });\n}";
				StringBuilder builder = new StringBuilder(js);
				Boolean wrap = (Boolean) options.hash("wrap", Boolean.valueOf(false));
				if (wrap.booleanValue()) {
					builder.append("<script type=\'text/javascript\'>\n");
				}

				builder.append(js);
				if (wrap.booleanValue()) {
					builder.append("</script>");
				}

				return new SafeString(builder);
			}
		});
	}

	private String message(String message) {
		Matcher matcher = PATTERN.matcher(message);
		StringBuffer result = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(result, "{{arg" + matcher.group(1) + "}}");
		}

		matcher.appendTail(result);
		return result.toString();
	}
}
