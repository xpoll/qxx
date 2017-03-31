package com.blmdz.bt.control;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.blmdz.bt.config.OneConfig;
import com.blmdz.bt.config.TwoConfig;

@Configuration
@Import({
	OneConfig.class,
	TwoConfig.class})
public class Appconfig {
//	public static void main(String[] args) {
//		ApplicationContext context = new AnnotationConfigApplicationContext(Appconfig.class);
//		OneEntity one = (OneEntity) context.getBean("one");
//		TwoEntity two = (TwoEntity) context.getBean("two");
//		one.printMsg("test");
//		two.printMsg("test");
//	}
}
