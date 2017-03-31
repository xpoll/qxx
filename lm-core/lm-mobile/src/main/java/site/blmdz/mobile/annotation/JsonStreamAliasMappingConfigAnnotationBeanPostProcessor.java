package site.blmdz.mobile.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class JsonStreamAliasMappingConfigAnnotationBeanPostProcessor extends
		InstantiationAwareBeanPostProcessorAdapter {
	private final static Log log = LogFactory.getLog(JsonStreamAliasMappingConfigAnnotationBeanPostProcessor.class);
	
	private final static Map<String, Map<String,Class<?>>> xstreamAliasMap = new HashMap<String, Map<String,Class<?>>>();
	
	private final static Map<String, Class<?>> responseClassMap = new HashMap<String, Class<?>>();
	
	private final static Map<String, Boolean> securityMap = new HashMap<String, Boolean>();
	
	public static Boolean getSecurityMap(String url){
		Boolean b = securityMap.get(url);
		if(b == null){
			throw new NullPointerException("securityMap is not defined. url=" + url);
		}
		return b;
	}
	
	public static Map<String,Class<?>> getXStreamAliasMap(String url){
		Map<String,Class<?>> map = xstreamAliasMap.get(url);
		if(map == null){
			throw new NullPointerException("xstreamAliasMap is not defined. url=" + url);
		}
		return map;
	}
	
	public static Class<?> getResponseClassMap(String url){
		Class<?> clazz = responseClassMap.get(url);
		if(clazz == null){
			throw new NullPointerException("responseClassMap is not defined. url=" + url);
		}
		return clazz;
	}
	
	
	@Override
	public boolean postProcessAfterInstantiation(final Object bean, String beanName)
			throws BeansException {
		ReflectionUtils.doWithMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {
			public void doWith(Method method) throws IllegalArgumentException,
					IllegalAccessException {
				JsonStreamAliasMapping xam = method.getAnnotation(JsonStreamAliasMapping.class);
				if(xam != null){
					String url = xam.url();
					String in = xam.in();
					Class<?> incls = xam.incls();
					
					String out = xam.out();
					Class<?> outcls = xam.outcls();
					
					String inListItem = xam.inListItem();
					Class<?> inListItemCls = xam.inListItemCls();
					
					boolean sec = xam.security();
					
					Map<String,Class<?>> map = new HashMap<String,Class<?>>();
					map.put(in, incls);
					if(inListItemCls != null){
						map.put(inListItem, inListItemCls);
					}
					map.put(out, outcls);
					xstreamAliasMap.put(url, map);
					responseClassMap.put(url, outcls);
					securityMap.put(url, sec);
					log.debug("put xstreamAliasMap url=" + url + ", in="+ in +",incls=" + incls.getSimpleName()+ ", out="+ out +",outcls=" + outcls.getSimpleName());
				}
			}
		});
		return true;
	}

}
