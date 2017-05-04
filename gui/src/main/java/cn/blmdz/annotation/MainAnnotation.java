package cn.blmdz.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 自定义注解
 */
public class MainAnnotation {

	public static void main(String[] args) {
		example1();
		example2();
		example3();
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
