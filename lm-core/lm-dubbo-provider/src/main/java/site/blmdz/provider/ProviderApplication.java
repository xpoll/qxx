package site.blmdz.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = 
        		new SpringApplication(ProviderApplication.class
        				, "classpath:dubbo-provider.xml");
        application.run(args);
    }
}
