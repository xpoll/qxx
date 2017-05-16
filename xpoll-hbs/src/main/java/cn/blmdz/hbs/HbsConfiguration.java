package cn.blmdz.hbs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.blmdz.hbs.config.HbsProperties;
import cn.blmdz.hbs.filter.PageErrorFilter;

/**
 * 额外总配置文件
 * @author yangyz
 * @date 2016年12月2日下午5:22:26
 */
@Configuration
@EnableConfigurationProperties(HbsProperties.class)
public class HbsConfiguration extends WebMvcConfigurerAdapter {


	@Bean
	public PageErrorFilter filter() {
		return new PageErrorFilter(true);
	}
	
}
