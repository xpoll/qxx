package cn.blmdz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import cn.blmdz.config.GlobalExceptionHandler;

@Import(GlobalExceptionHandler.class)
@SpringBootApplication
@ComponentScan("cn.blmdz.component")
public class XpollApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(XpollApplication.class);
		application.run(args);
	}
}
