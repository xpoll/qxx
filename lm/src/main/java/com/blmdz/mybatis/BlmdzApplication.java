package com.blmdz.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlmdzApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BlmdzApplication.class);
		application.run(args);
	}
}
