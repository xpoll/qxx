package com.blmdz.bt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blmdz.bt.core.OneEntity;

@Configuration
public class OneConfig {
	@Bean(name="one")
	public OneEntity entity(){
		return new OneEntity();
	}
}
