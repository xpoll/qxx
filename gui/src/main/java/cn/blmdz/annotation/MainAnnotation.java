package cn.blmdz.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 自定义注解
 */
public class MainAnnotation {

	public static void main(String[] args) throws Exception {
		example1();
		example2();
		example3();
		example4();
	}

	public static void example4() throws Exception {
//		Example.class.getMethod("test", parameterTypes)
		for(Method m : Example.class.getMethods()) {
			for(Annotation a : m.getAnnotations()) {
				if (a instanceof MyAnnotation) {
					if (m.getName() == "test") {
						for (Class<?> c : m.getParameterTypes()) {
							System.out.println(c.getSimpleName());
						}
						for (Type type : m.getGenericParameterTypes()) {
							System.out.println(type);
						}
						System.out.println(m.invoke(Example.class.newInstance(), "af", 1));
					}
				}
			}
		}
	}
	
	public static void example1() {
		for(Annotation a : Example.class.getAnnotations()) {
			if (a instanceof MyAnnotation) {
				MyAnnotation my = (MyAnnotation) a;
				System.out.println(my.value());
			}
		}
		
		for(Method m : Example.class.getMethods()) {
			for(Annotation a : m.getAnnotations()) {
				if (a instanceof MyAnnotation) {
					MyAnnotation my = (MyAnnotation) a;
					System.out.println(my.value());
				}
			}
			
		}
	}
	
	public static void example2() {
		for(Annotation a : Example2.class.getAnnotations()) {
			if (a instanceof MyAnnotation) {
				MyAnnotation my = (MyAnnotation) a;
				System.out.println(my.value());
			}
		}
		
		for(Method m : Example2.class.getMethods()) {
			for(Annotation a : m.getAnnotations()) {
				if (a instanceof MyAnnotation) {
					MyAnnotation my = (MyAnnotation) a;
					System.out.println(my.value());
				}
			}
			
		}
	}
	
	public static void example3() {
		for(Annotation a : Example3.class.getAnnotations()) {
			if (a instanceof MyAnnotation) {
				MyAnnotation my = (MyAnnotation) a;
				System.out.println(my.value());
			}
		}
		
		for(Method m : Example3.class.getMethods()) {
			for(Annotation a : m.getAnnotations()) {
				if (a instanceof MyAnnotation) {
					MyAnnotation my = (MyAnnotation) a;
					System.out.println(my.value());
				}
			}
			
		}
	}
}
