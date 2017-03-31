package blmdz.site.tt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
public class WsmApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WsmApplication.class
        		, "classpath:/spring/lm-ws-dubbo-provider.xml");
        application.run(args);
    }
}
