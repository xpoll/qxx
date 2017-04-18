package cn.blmdz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.eventbus.EventBus;

import cn.blmdz.filter.PageErrorFilter;

/**
 * 额外总配置文件
 * @author yangyz
 * @date 2016年12月2日下午5:22:26
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {


	@Bean
	public PageErrorFilter filter() {
		return new PageErrorFilter(true);
	}
	
	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}
}
