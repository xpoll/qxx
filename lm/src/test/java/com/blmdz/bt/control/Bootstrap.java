package com.blmdz.bt.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Import(CacheConfig.class)
//@ComponentScan(basePackages={"com.blmdz.redis"})
public class Bootstrap {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Bootstrap.class);
		application.run(args);
	}
}
