package site.blmdz.base.session.util;

import lombok.Data;

@Data
public class Config {
	private String source = "redis";//储存源
	private String serializeType = "json";//序列类型
	private String redisPrefix;//redis前缀
	private Boolean redisCluster = Boolean.FALSE.booleanValue();//redis集群
	private Boolean redisTestOnBorrow = Boolean.TRUE.booleanValue();//true:获取都为可用的实例对象
	private Integer redisMaxIdle = 0;//控制一个pool最多有多少个状态为idle的jedis实例
	private Integer redisMaxTotal = 5;
	private Integer redisIndex = 0;
	private String redisHost;
	private Integer redisPort;
	private String redisSentinelMasterName;//实例名
	private String redisSentinelHosts;//哨兵host
	private String redisAuth;
}
