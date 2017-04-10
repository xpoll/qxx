package cn.blmdz.hunt.engine.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface PampasProfiled {
	@AliasFor("key")
	String value() default "";

	@AliasFor("value")
	String key() default "";

	boolean withParams() default true;

	boolean withOutput() default true;

	boolean onlyWhenException() default false;
}