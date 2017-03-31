package site.blmdz.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RedisApplication.class);
        application.run(args);
    }
}
