package cn.blmdz.hbs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@EnableWebMvc
@SpringBootApplication
public class HbsApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HbsApplication.class);
        application.run(args);
    }
}
