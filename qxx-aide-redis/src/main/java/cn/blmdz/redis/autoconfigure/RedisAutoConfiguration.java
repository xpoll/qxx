package cn.blmdz.redis.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.redis.properties.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {
	@Autowired
	private RedisProperties properties;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(this.properties.getMaxTotal());
		config.setMaxIdle(this.properties.getMaxIdle());
		config.setMaxWaitMillis(properties.getMaxWaitMillis());
		config.setTestOnBorrow(this.properties.isTestOnBorrow());
		return config;
	}

	@Bean
	public Pool<Jedis> jedisPool() {
		if (this.properties.isCluster()) {
			String sentinelProps = this.properties.getSentinelHosts();
			Iterable<String> parts = Splitter.on(',').trimResults()
					.omitEmptyStrings().split(sentinelProps);
			return new JedisSentinelPool(
					this.properties.getSentinelMasterName(),
					Sets.newHashSet(parts),
					this.jedisPoolConfig(),
					this.properties.getTimeout(),
					this.properties.getAuth());
		} else {
			return new JedisPool(
					this.jedisPoolConfig(),
					this.properties.getHost(),
					this.properties.getPort(),
					this.properties.getTimeout(),
					this.properties.getAuth());
		}
	}

	@Bean
	public JedisExecutor jedisExecutor() {
		return new JedisExecutor(this.jedisPool());
	}
}
