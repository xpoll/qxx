package cn.blmdz.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.blmdz.hbs.config.HbsProperties;
import cn.blmdz.web.exception.GlobalExceptionHandler;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@EnableWebMvc
@SpringBootApplication
@EnableConfigurationProperties(HbsProperties.class)
@Import(GlobalExceptionHandler.class)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.run(args);
    }
}
