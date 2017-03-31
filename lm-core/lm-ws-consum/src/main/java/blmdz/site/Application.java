package blmdz.site;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class
        		/*, "classpath:/spring/lm-ws-dubbo-provider.xml"*/);
        application.run(args);
    }
}
