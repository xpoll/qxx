package site.blmdz.spring.boot.session.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import site.blmdz.base.session.BlmdzSessionFilter;
import site.blmdz.base.session.BlmdzSessionManager;
import site.blmdz.base.session.util.Config;
import site.blmdz.spring.boot.session.properties.SessionProperties;

@Configuration
@EnableConfigurationProperties(SessionProperties.class)
public class SessionAutoConfiguration {
	
	private Logger log = LoggerFactory.getLogger(SessionAutoConfiguration.class);
	
	@Autowired
	private SessionProperties properties;
	
	@Bean
	@ConditionalOnProperty(
			prefix="session",
			name="mode",
			havingValue="session",
			matchIfMissing=true)
	public FilterRegistrationBean blmdzSessionFilterRegistration() {
		log.debug("filter regist -> BlmdzSessionFilter");
		FilterRegistrationBean registration = new FilterRegistrationBean();
		BlmdzSessionFilter filter = new BlmdzSessionFilter();
		filter.setMaxInactiveInterval(properties.getMaxInactiveInterval());
		filter.setCookieMaxAge(properties.getCookieMaxAge());
		filter.setCookieName(properties.getCookieName());
		filter.setCookieDomain(properties.getCookieDomain());
		filter.setCookieContextPath(properties.getCookieContextPath());
		filter.setRedisPrefix(properties.getRedisPrefix());
		filter.setSessionManager(sessionManager());
		registration.setFilter(filter);
		registration.addUrlPatterns(new String[] { "/*" });
		registration.addInitParameter("cookieName", properties.getCookieName());
		registration.addInitParameter("maxInactiveInterval", String.valueOf(properties.getMaxInactiveInterval()));
		registration.addInitParameter("cookieDomain", properties.getCookieDomain());
		registration.addInitParameter("cookieContextPath", properties.getCookieContextPath());
		registration.setName("BlmdzSessionFilter");
		return registration;
	}
	@Bean
	public BlmdzSessionManager sessionManager() {
		Config config = new Config();
		config.setSource(properties.getSource());
		config.setSerializeType(properties.getSerializeType());
		config.setRedisPrefix(properties.getRedisPrefix());
		config.setRedisCluster(properties.getRedisCluster());
		config.setRedisTestOnBorrow(properties.getRedisTestOnBorrow());
		config.setRedisMaxIdle(properties.getRedisMaxIdle());
		config.setRedisMaxTotal(properties.getRedisMaxTotal());
		config.setRedisIndex(properties.getRedisIndex());
		config.setRedisHost(properties.getRedisHost());
		config.setRedisPort(properties.getRedisPort());
		config.setRedisSentinelMasterName(properties.getRedisSentinelMasterName());
		config.setRedisSentinelHosts(properties.getRedisSentinelHosts());
		config.setRedisAuth(properties.getRedisAuth());
		return BlmdzSessionManager.newInstance(config);
	}
}
