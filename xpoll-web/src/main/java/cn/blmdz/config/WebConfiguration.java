package cn.blmdz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.eventbus.EventBus;

import cn.blmdz.aide.file.ImageServer;
import cn.blmdz.aide.file.aliyun.AliyunImageServer;
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
	
	@Bean
	public ImageServer imageServer(
			@Value("${oos.endpoint}") String endpoint,
			@Value("${oos.accessKey}") String appKey,
			@Value("${oos.accessSecret}") String appSecret,
			@Value("${oos.bucketName}") String bucketName
			){
		return new AliyunImageServer(endpoint, appKey, appSecret, bucketName);
	}
}
