package cn.blmdz.session.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.session.QxxSessionFilter;
import cn.blmdz.session.QxxSessionManager;
import cn.blmdz.session.properties.SessionProperties;
import cn.blmdz.session.service.SessionRedisSource;

@Configuration
@ConditionalOnBean(JedisExecutor.class)
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
	public FilterRegistrationBean qxxSessionFilterRegistration(QxxSessionManager qxxSessionManager) {
		log.debug("filter regist -> QxxSessionFilter");
		FilterRegistrationBean registration = new FilterRegistrationBean();
		QxxSessionFilter filter = new QxxSessionFilter();
		filter.setMaxInactiveInterval(properties.getMaxInactiveInterval());
		filter.setCookieMaxAge(properties.getCookieMaxAge());
		filter.setCookieName(properties.getCookieName());
		filter.setCookieDomain(properties.getCookieDomain());
		filter.setCookieContextPath(properties.getCookieContextPath());
		filter.setRedisPrefix(properties.getRedisPrefix());
		filter.setSessionManager(qxxSessionManager);
		registration.setFilter(filter);
		registration.addUrlPatterns(new String[] { "/*" });
		registration.addInitParameter("cookieName", properties.getCookieName());
		registration.addInitParameter("maxInactiveInterval", String.valueOf(properties.getMaxInactiveInterval()));
		registration.addInitParameter("cookieDomain", properties.getCookieDomain());
		registration.addInitParameter("cookieContextPath", properties.getCookieContextPath());
		registration.setName("QxxSessionFilter");
		return registration;
	}
	@Bean
	public QxxSessionManager sessionManager(JedisExecutor jedisExecutor) {
		return QxxSessionManager.newInstance(new SessionRedisSource(jedisExecutor, properties));
	}
}
