package site.blmdz.spring.boot.session.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix="session")
public class SessionProperties {
	private String source;//储存源
	private String serializeType = "json";//序列号类型
	private String redisPrefix;//redis前缀
	private Boolean redisCluster = Boolean.FALSE.booleanValue();//redis集群
	private Boolean redisTestOnBorrow = Boolean.TRUE.booleanValue();//true:获取都为可用的实例对象
	private int redisMaxIdle = 0;//控制一个pool最多有多少个状态为idle的jedis实例
	private int redisMaxTotal = 5;
	private int redisIndex = 0;
	private String cookieName;
	private int maxInactiveInterval;//超时时间s
	private int cookieMaxAge = -1;//cookie最大存活时间
	private String cookieContextPath = "/";//cookie储存位置
	private String redisHost;
	private int redisPort;
	private String redisSentinelMasterName;//实例名
	private String redisSentinelHosts;//哨兵host
	private String redisAuth;
	private String cookieDomain;
}
