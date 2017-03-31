

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
public class ApplicationTest {  
  
    private static final Logger log = LoggerFactory.getLogger(ApplicationTest.class);  
  
    @Component  
    static class Runner implements CommandLineRunner {  
  
        @Override  
        public void run(String... args) throws Exception {  
            log.info("1");  
            log.info("2");  
            CountDownLatch countDownLatch = new CountDownLatch(1);
            log.info("3");
            countDownLatch.await();
            log.info("4");  
            countDownLatch.countDown();
            log.info("5");
        }  
    }  
  
    public static void main(String[] args) {  
        SpringApplication.run(ApplicationTest.class, args);  
    }  
}  
