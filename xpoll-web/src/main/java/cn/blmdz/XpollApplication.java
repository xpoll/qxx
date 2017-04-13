package cn.blmdz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.blmdz.config.GlobalExceptionHandler;
import cn.blmdz.config.WebConfiguration;

@Import({GlobalExceptionHandler.class, WebConfiguration.class})
@SpringBootApplication
@EnableWebMvc
public class XpollApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(XpollApplication.class);
		application.run(args);
	}
}
