package com.blmdz.bt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value="classpath:config.properties",ignoreResourceNotFound=false)
@EnableCaching
@EnableAutoConfiguration
public class CacheConfig /*extends CachingConfigurerSupport*/{
	@Value("${mongodb.url}")
	private String mongodbUrl;
	@Autowired
	private Environment env;
	
	@Bean
	public Object ab(){
		System.out.println(mongodbUrl);
		System.out.println(env.getProperty("mongodb.url"));
		return new Object();
	}
}
