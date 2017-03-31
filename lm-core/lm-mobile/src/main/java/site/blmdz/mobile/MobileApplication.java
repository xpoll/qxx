package site.blmdz.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MobileApplication.class);
        application.run(args);
    }
}
