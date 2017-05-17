package cn.blmdz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.eventbus.EventBus;

import cn.blmdz.aide.file.ImageServer;
import cn.blmdz.aide.file.aliyun.AliyunImageServer;
import cn.blmdz.hbs.config.HbsProperties;
import cn.blmdz.hbs.filter.PageErrorFilter;
import cn.blmdz.web.interceptors.LoginInterceptor;

/**
 * 额外总配置文件
 * 
 * @author yangyz
 * @date 2016年12月2日下午5:22:26
 */
@Configuration
@ComponentScan("cn.blmdz.hbs")
public class WebConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public PageErrorFilter filter() {
		return new PageErrorFilter(true);
	}
	
	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

	@Bean
	public ImageServer imageServer(HbsProperties hbsProperties) {

		return new AliyunImageServer(
				hbsProperties.getOos().getEndpoint(),
				hbsProperties.getOos().getAccessKey(),
				hbsProperties.getOos().getAccessSecret(),
				hbsProperties.getOos().getBucketName());
	}
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(applicationContext.getBean(LoginInterceptor.class));
	}
}
