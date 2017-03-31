package site.blmdz.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "site.blmdz.redis.service")
public class RedisConfiguration {
	@Bean(name="abc")
	public Object abc(@Value("${redis.host}") String host, @Value("${redis.port}") int port){
		System.out.println("host------>" + host);
		System.out.println("port------>" + port);
		return null;
	}
	@Bean
	@ConditionalOnMissingBean(name = "abc") //满足不存在
	@ConditionalOnProperty(prefix="redis", name="host") //满足存在
	public Object abcd(){
		System.out.println("abcd init ..");
		return null;
	}
}
