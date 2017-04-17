package cn.blmdz.session.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix="session")
public class SessionProperties {
	private String source;//储存源
	private String redisPrefix;//redis前缀
	private int redisIndex = 0;
	private String cookieName;
	private int maxInactiveInterval = 1800;//超时时间s
	private int cookieMaxAge = -1;//cookie最大存活时间
	private String cookieContextPath = "/";//cookie储存位置
	private String cookieDomain;
}
