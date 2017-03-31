package site.blmdz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WspApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WspApplication.class);
        application.run(args);
    }
}
