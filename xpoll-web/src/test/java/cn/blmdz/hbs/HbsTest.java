package cn.blmdz.hbs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HbsTest {
	
	public static void main(String[] args) throws IOException {
//		test1();
		test2();
	}
	
	public static void test1() throws IOException{
		Handlebars hbs = new Handlebars();
		Template tem = hbs.compileInline("hello,{{this}}");
		System.out.println(tem.apply("handlebars"));
	}
	public static void test2() throws IOException{
		TemplateLoader load = new ClassPathTemplateLoader("/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		hbs.registerHelper("show_index", new Helper<Integer>() {
			@Override
			public CharSequence apply(Integer value, Options arg1)
					throws IOException {
				return String.valueOf(value + 1);
			}
		});
		hbs.registerHelper("compare", new Helper<Object>() {
			@Override
			public CharSequence apply(Object obj, Options options)
					throws IOException {
				long source;
				if(obj instanceof Long)
					source = (Long)obj;
				else if(obj instanceof Integer)
					source = (Integer) obj;
				else
					source = Long.parseLong((String)obj);
				if(source > (Integer) options.param(0))
					return options.fn();
				else
					return options.inverse();
			}
		});
		Template tem = hbs.compile("mytemplate");
		Map<String, Object> content = Maps.newHashMap();
		A a = new A();
		a.setName("tom");
		a.setAge(18);
		List<String> info = Lists.newArrayList();
		info.add("look watch TV");
		info.add("play games");
		a.setInfo(info);
		content.put("a", a);
		System.out.println(tem.apply(content));
	}
}

@Data
class A {
	private String name;
	private int age;
	private List<String> info;
}
