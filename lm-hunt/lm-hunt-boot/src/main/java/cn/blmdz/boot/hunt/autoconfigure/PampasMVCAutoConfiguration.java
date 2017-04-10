package cn.blmdz.boot.hunt.autoconfigure;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.boot.hunt.autoconfigure.PampasMVCProperties.Interceptors;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;
import cn.blmdz.hunt.webc.converter.JsonMessageConverter;
import cn.blmdz.hunt.webc.resolver.ExceptionResolver;
import cn.blmdz.hunt.webc.resolver.HandlebarsViewResolver;

@Configuration
@ConditionalOnProperty(prefix = "pampas", name = { "autoConfigureMVC" }, matchIfMissing = true)
@EnableConfigurationProperties(PampasMVCProperties.class)
@EnableWebMvc
@ComponentScan("cn.blmdz.hunt.webc")
@AutoConfigureAfter(PampasAutoConfiguration.class)
public class PampasMVCAutoConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private PampasMVCProperties properties;
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		Map<Interceptors, HandlerInterceptor> interceptors = Maps.newLinkedHashMap();
		interceptors.put(Interceptors.CSRFCheck,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasCSRFCheckInterceptor"));
		interceptors.put(PampasMVCProperties.Interceptors.App,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasAppInterceptor"));
		interceptors.put(PampasMVCProperties.Interceptors.LocaleJudge,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasLocaleJudgeInterceptor"));
		interceptors.put(PampasMVCProperties.Interceptors.Cookie,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasCookieInterceptor"));
		interceptors.put(PampasMVCProperties.Interceptors.Login,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasLoginInterceptor"));
		interceptors.put(PampasMVCProperties.Interceptors.Auth,
				(HandlerInterceptorAdapter) applicationContext.getBean("pampasAuthInterceptor"));
		if (properties.getIgnoreInterceptors() != null) {
			for (Interceptors key : properties.getIgnoreInterceptors()) {
				interceptors.remove(key);
			}
		}

		for (HandlerInterceptor interceptor : interceptors.values()) {
			registry.addInterceptor(interceptor);
		}

		if (properties.getCustomInterceptors() != null) {
			for (String beanName : properties.getCustomInterceptors()) {
				registry.addInterceptor((HandlerInterceptor) applicationContext.getBean(beanName));
			}
		}

	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterators.removeIf(converters.iterator(), new Predicate<HttpMessageConverter<?>>() {
			@Override
			public boolean apply(HttpMessageConverter<?> input) {
				return input instanceof StringHttpMessageConverter;
			}
		});
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charsets.UTF_8);
		stringHttpMessageConverter
				.setSupportedMediaTypes(Lists.newArrayList(new MediaType[] { MediaType.TEXT_PLAIN, MediaType.ALL }));
		converters.add(1, stringHttpMessageConverter);
		converters.add(new JsonMessageConverter());
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver(applicationContext.getBean(HandlebarsEngine.class));
		registry.viewResolver(viewResolver);
	}

	@Bean
	public LocaleContextResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return localeResolver;
	}

	@Bean
	public ExceptionHandlerExceptionResolver exceptionResolver() {
		ExceptionResolver exceptionResolver = new ExceptionResolver();
		exceptionResolver.setOrder(Integer.MIN_VALUE);
		if (!Strings.isNullOrEmpty(properties.getDefaultErrorView())) {
			exceptionResolver.setDefaultErrorView(properties.getDefaultErrorView());
		}

		exceptionResolver.setCodeErrorViews(properties.getCodeErrorViews());
		return exceptionResolver;
	}

	@Bean
	public Filter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
