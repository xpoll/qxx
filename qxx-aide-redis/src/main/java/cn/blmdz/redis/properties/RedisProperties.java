package cn.blmdz.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix="redis")
public class RedisProperties {
	private String host = "127.0.0.1";
	private int port = 6379;
	private String auth;
	
	private boolean testOnBorrow = Boolean.TRUE.booleanValue();//true:获取都为可用的实例对象
	private int maxIdle = 0;//控制一个pool最多有多少个状态为idle的jedis实例
	private int maxTotal = 10;
	private int maxWaitMillis = 10000;
	private int timeout = 2000;
	
	private boolean cluster = Boolean.FALSE.booleanValue();//redis集群
	private String sentinelMasterName;//实例名
	private String sentinelHosts;//哨兵host
}
