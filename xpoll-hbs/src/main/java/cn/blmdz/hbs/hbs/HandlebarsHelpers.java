package cn.blmdz.hbs.hbs;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.hbs.util.JsonMapper;
import cn.blmdz.hbs.util.Logics;
import cn.blmdz.hbs.util.MapBuilder;
import cn.blmdz.hbs.util.NumberUtils;
import cn.blmdz.hbs.util.Numbers;
import cn.blmdz.hbs.util.Splitters;

@Component
public class HandlebarsHelpers extends AbstractHelpers {
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

	@Override
	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("ifCond", new Helper<Object>() {
			@Override
			public CharSequence apply(Object p1, Options options) throws IOException {
				String operator = options.param(0);
				Object p2 = options.param(1);
				boolean isTrue = false;
				byte var7 = -1;
				switch (operator.hashCode()) {
				case 60:
					if (operator.equals("<")) {
						var7 = 6;
					}
					break;
				case 62:
					if (operator.equals(">")) {
						var7 = 8;
					}
					break;
				case 1084:
					if (operator.equals("!=")) {
						var7 = 1;
					}
					break;
				case 1216:
					if (operator.equals("&&")) {
						var7 = 4;
					}
					break;
				case 1921:
					if (operator.equals("<=")) {
						var7 = 7;
					}
					break;
				case 1952:
					if (operator.equals("==")) {
						var7 = 0;
					}
					break;
				case 1983:
					if (operator.equals(">=")) {
						var7 = 9;
					}
					break;
				case 3968:
					if (operator.equals("||")) {
						var7 = 5;
					}
					break;
				case 33665:
					if (operator.equals("!==")) {
						var7 = 3;
					}
					break;
				case 60573:
					if (operator.equals("===")) {
						var7 = 2;
					}
				}

				switch (var7) {
				case 0:
					isTrue = Objects.equal(p1, p2);
					break;
				case 1:
					isTrue = !Objects.equal(p1, p2);
					break;
				case 2:
					isTrue = p1 == p2;
					break;
				case 3:
					isTrue = p1 != p2;
					break;
				case 4:
					isTrue = Logics.and(p1, p2);
					break;
				case 5:
					isTrue = Logics.or(p1, p2);
					break;
				case 6:
					isTrue = Numbers.compare(p1, p2) == -1;
					break;
				case 7:
					isTrue = Numbers.compare(p1, p2) <= 0;
					break;
				case 8:
					isTrue = Numbers.compare(p1, p2) == 1;
					break;
				case 9:
					isTrue = Numbers.compare(p1, p2) >= 0;
				}

				return isTrue ? options.fn() : options.inverse();
			}
		});
		helpers.put("assign", new Helper<String>() {
			@Override
			public CharSequence apply(String name, Options options) throws IOException {
				CharSequence finalValue = options.apply(options.fn);
				options.context.data(name, finalValue.toString().trim());
				return null;
			}
		});
		helpers.put("json", new Helper<Object>() {
			@Override
			public CharSequence apply(Object context, Options options) throws IOException {
				return JsonMapper.nonEmptyMapper().toJson(context);
			}
		});
		helpers.put("match", new Helper<String>() {
			@Override
			public CharSequence apply(String regEx, Options options) throws IOException {
				Pattern pat = Pattern.compile(regEx);
				Matcher mat = pat.matcher((String) options.param(0));
				return mat.find() ? options.fn() : options.inverse();
			}
		});
		helpers.put("gt", new Helper<Object>() {
			@Override
			public CharSequence apply(Object source, Options options) throws IOException {
				long _source;
				if (source instanceof Long) {
					_source = ((Long) source).longValue();
				} else if (source instanceof Integer) {
					_source = (long) ((Integer) source).intValue();
				} else {
					_source = Long.parseLong((String) source);
				}

				return _source > (long) ((Integer) options.param(0)).intValue() ? options.fn() : options.inverse();
			}
		});
		helpers.put("mod", new Helper<Integer>() {
			@Override
			public CharSequence apply(Integer source, Options options) throws IOException {
				return (source.intValue() + 1) % ((Integer) options.param(0)).intValue() == 0 ? options.fn()
						: options.inverse();
			}
		});
		helpers.put("size", new Helper<Object>() {
			@Override
			public CharSequence apply(Object context, Options options) throws IOException {
				return context == null ? "0"
						: (context instanceof Collection ? String.valueOf(((Collection<?>) context).size())
								: (context instanceof Map ? String.valueOf(((Map<?, ?>) context).size()) : "0"));
			}
		});
		helpers.put("equals", new Helper<Object>() {
			@Override
			public CharSequence apply(Object source, Options options) throws IOException {
				return Objects.equal(String.valueOf(source), String.valueOf(options.param(0))) ? options.fn()
						: options.inverse();
			}
		});
		helpers.put("formatDate", new Helper<Object>() {
			Map<Object, Object> sdfMap = MapBuilder.of()
					.put("gmt", new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy"), "day",
							new SimpleDateFormat("yyyy-MM-dd"), "default", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
					.map();
			@Override
			public CharSequence apply(Object dateObj, Options options) throws IOException {
				if (dateObj == null) {
					return "";
				} else {
					Date date = (new DateTime(dateObj)).toDate();
					String format = (String) options.param(0, "default");
					if (format.equals("ut")) {
						return Long.toString(date.getTime());
					} else {
						if (!this.sdfMap.containsKey(format)) {
							this.sdfMap.put(format, new SimpleDateFormat(format));
						}

						return ((SimpleDateFormat) this.sdfMap.get(format)).format(date);
					}
				}
			}
		});
		helpers.put("formatPrice", new Helper<Number>() {
			@Override
			public CharSequence apply(Number price, Options options) throws IOException {
				return NumberUtils.formatPrice(price);
			}
		});
		helpers.put("innerStyle", new Helper<Object>() {
			@Override
			public CharSequence apply(Object context, Options options) throws IOException {
				if (context == null) {
					return "";
				} else {
					StringBuilder ret = new StringBuilder();
					String[] styles = ((String) context).split(";");

					for (String style : styles) {
						String key = style.split(":")[0];
						if (key.endsWith("radius")) {
							ret.append(style).append(";");
						}
					}

					return ret;
				}
			}
		});
		helpers.put("cIndex", new Helper<Integer>() {
			@Override
			public CharSequence apply(Integer context, Options options) throws IOException {
				return "" + (char) (context.intValue() + 65);
			}
		});
		helpers.put("formatRate", new Helper<Double>() {
			@Override
			public CharSequence apply(Double rate, Options options) throws IOException {
				return rate == null ? "" : HandlebarsHelpers.DECIMAL_FORMAT.format(rate.doubleValue() / 1000.0D);
			}
		});
		helpers.put("formatIntegerRate", new Helper<Integer>() {
			@Override
			public CharSequence apply(Integer rate, Options options) throws IOException {
				return rate == null ? "" : HandlebarsHelpers.DECIMAL_FORMAT.format((double) rate.intValue() / 1000.0D);
			}
		});
		helpers.put("of", new Helper<Object>() {
			@Override
			public CharSequence apply(Object source, Options options) throws IOException {
				if (source == null) {
					return options.inverse();
				} else {
					String _source = source.toString();
					String param = (String) options.param(0);
					if (Strings.isNullOrEmpty(param)) {
						return options.inverse();
					} else {
						List<String> targets = Splitters.COMMA.splitToList(param);
						return targets.contains(_source) ? options.fn() : options.inverse();
					}
				}
			}
		});
		helpers.put("add", new Helper<Object>() {
			@Override
			public CharSequence apply(Object source, Options options) throws IOException {
				Object param = options.param(0);
				if (source == null && param == null) {
					return "";
				} else if (source == null) {
					return param.toString();
				} else if (param == null) {
					return source.toString();
				} else if (source instanceof Double) {
					Double first = (Double) source;
					Double second = (Double) param;
					return String.valueOf(first.doubleValue() + second.doubleValue());
				} else if (source instanceof Integer) {
					Integer first = (Integer) source;
					Integer second = (Integer) param;
					return String.valueOf(first.intValue() + second.intValue());
				} else if (source instanceof Long) {
					Long first = (Long) source;
					Long second = (Long) param;
					return String.valueOf(first.longValue() + second.longValue());
				} else if (source instanceof String) {
					Integer first = Integer.valueOf(Integer.parseInt(source.toString()));
					Integer second = Integer.valueOf(Integer.parseInt(param.toString()));
					return String.valueOf(first.intValue() + second.intValue());
				} else {
					throw new IllegalStateException("incorrect.type");
				}
			}
		});
		helpers.put("rget", new Helper<Object>() {
			private final Random random = new Random(System.currentTimeMillis());

			@Override
			@SuppressWarnings("unchecked")
			public CharSequence apply(Object context, Options options) throws IOException {
				List<String> list;
				if (context instanceof List) {
					list = (List<String>) context;
				} else {
					list = Splitters.COMMA.splitToList(String.valueOf(context));
				}

				return list.isEmpty() ? null : list.get(this.random.nextInt(list.size())).toString();
			}
		});
		helpers.put("urlEncode", new Helper<Object>() {
			@Override
			public CharSequence apply(Object param, Options options) throws IOException {
				String charset = (String) options.param(0, "utf-8");
				return URLEncoder.encode(param.toString(), charset);
			}
		});
//		helpers.put("debug", new Helper<Object>() {
//			@Override
//			public CharSequence apply(Object o, Options options) throws IOException {
//				return options.fn();
//			}
//		});
		helpers.put("mapping", new Helper<Object>() {
			@Override
			@SuppressWarnings("unchecked")
			public CharSequence apply(Object param, Options options) throws IOException {
				CharSequence mappingJson = options.fn();
				Map<String, Object> mappingMap = JsonMapper.nonEmptyMapper().fromJson(String.valueOf(mappingJson), Map.class);
				return String.valueOf(mappingMap.get(param.toString()));
			}
		});
	}
}
