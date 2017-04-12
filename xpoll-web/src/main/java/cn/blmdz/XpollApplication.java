package cn.blmdz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.blmdz.config.GlobalExceptionHandler;

@Import(GlobalExceptionHandler.class)
@SpringBootApplication
@EnableWebMvc
@ComponentScan("io.terminus.galaxy.web.design.service")
public class XpollApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(XpollApplication.class);
		application.run(args);
	}
}
