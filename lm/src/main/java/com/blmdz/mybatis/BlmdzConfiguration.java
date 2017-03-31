package com.blmdz.mybatis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {
		"com.blmdz.mybatis"
})
@EnableWebMvc
public class BlmdzConfiguration {

}
