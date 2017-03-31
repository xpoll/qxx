package site.blmdz.mobile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import site.blmdz.mobile.enums.Constants;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonStreamAliasMapping {

	abstract String url();
	
	String in() default Constants.IN;
	
	abstract Class<?> incls() ;
	
	String out() default Constants.OUT;
	
	abstract Class<?> outcls() ;
	
	String inListItem() default Constants.ITEM;
	
	Class<?> inListItemCls() default String.class;
	
	boolean security() default true;
	
}
