package cn.blmdz.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })// method方法 type类
@Retention(RetentionPolicy.RUNTIME)// 作用域
@Documented// 生成文档
@Inherited// 子类可以继承
public @interface MyAnnotation {

	String value() default "";
}
