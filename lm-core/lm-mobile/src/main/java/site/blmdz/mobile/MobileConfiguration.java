package site.blmdz.mobile;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import site.blmdz.mobile.interceptor.JsonSecurityHandlerInterceptorAdapter;

@Configuration
@EnableWebMvc
public class MobileConfiguration extends WebMvcConfigurerAdapter{
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JsonSecurityHandlerInterceptorAdapter());
	}
	
}
