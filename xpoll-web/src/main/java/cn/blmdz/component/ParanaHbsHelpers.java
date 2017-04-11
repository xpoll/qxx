package cn.blmdz.component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.model.BaseUser;
import cn.blmdz.hunt.common.util.UserUtil;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;

@Component
@ConditionalOnBean({ HandlebarsEngine.class })
public class ParanaHbsHelpers {
	private HandlebarsEngine handlebarsEngine;

	@Autowired
	public ParanaHbsHelpers(HandlebarsEngine handlebarsEngine) {
		this.handlebarsEngine = handlebarsEngine;
		this.handlebarsEngine.registerHelper("splitter", new Helper<String>() {
			public CharSequence apply(String context, Options options) throws IOException {
				try {
					String delimiter = (String) options.param(0);
					if (Strings.isNullOrEmpty(delimiter)) {
						return "";
					} else {
						Integer index = (Integer) options.param(1, Integer.valueOf(0));
						List<String> pieces = Splitter.on(delimiter).omitEmptyStrings().splitToList(context);
						return (CharSequence) pieces.get(index.intValue());
					}
				} catch (Exception var6) {
					return "";
				}
			}
		});
		// this.handlebarsEngine.registerHelper("cdnPath", new Helper<String>()
		// {
		// public CharSequence apply(String path, Options options) throws
		// IOException {
		// return
		// ParanaHbsHelpers.this.isEmpty(path)?"http://zcy-dev.img-cn-hangzhou.aliyuncs.com/system/error/image_not_found.001.jpeg":(options.params
		// != null && options.params.length !=
		// 0?(!path.trim().contains("aliyuncs.com")?path:path +
		// AliyunImageProperties.from(options.params).getQueryString()):path);
		// }
		// });
		this.handlebarsEngine.registerHelper("isEmpty", new Helper<Object>() {
			public CharSequence apply(Object context, Options options) throws IOException {
				return ParanaHbsHelpers.this.isEmpty(context) ? options.fn() : options.inverse();
			}
		});
		this.handlebarsEngine.registerHelper("withPerm", new Helper<String>() {
			public CharSequence apply(String context, Options options) throws IOException {
				return (CharSequence) (options.tagType == TagType.SECTION ? options.fn() : "");
			}
		});
		this.handlebarsEngine.registerHelper("hasRole", new Helper<String>() {
			public CharSequence apply(String key, Options options) throws IOException {
				BaseUser user = UserUtil.getCurrentUser();
				if (user != null && user.getRoles() != null && !user.getRoles().isEmpty()) {
					Pattern pat = Pattern.compile(key);

					for (String role : user.getRoles()) {
						if (pat.matcher(role).matches()) {
							return options.fn();
						}
					}

					return options.inverse();
				} else {
					return options.inverse();
				}
			}
		});
	}

	public boolean isEmpty(Object value) {
		return value == null ? true
				: (!(value instanceof CharSequence)
						? (value instanceof Collection ? ((Collection<?>) value).size() == 0
								: (value instanceof Iterable ? !((Iterable<?>) value).iterator().hasNext()
										: (value instanceof Boolean ? !((Boolean) value).booleanValue()
												: (value.getClass().isArray() ? Array.getLength(value) == 0
														: (value instanceof Number ? ((Number) value).intValue() == 0
																: false)))))
						: ((CharSequence) value).length() == 0 || ((CharSequence) value).equals("[]"));
	}
}
