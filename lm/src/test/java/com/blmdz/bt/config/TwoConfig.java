package com.blmdz.bt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blmdz.bt.core.TwoEntity;

@Configuration
public class TwoConfig {
	@Bean(name="two")
	public TwoEntity entity(){
		return new TwoEntity();
	}
}
