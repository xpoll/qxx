package site.blmdz.base.session.redis;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;
import site.blmdz.base.session.service.JedisCallBack;
import site.blmdz.base.session.util.Config;

public class JedisPoolExecutor {
	@Getter
	private volatile Pool<Jedis> jedisPool;
	
	public JedisPoolExecutor(JedisPoolConfig jedisConfig, boolean sentinel, Config config) {
		if(sentinel) {
			Iterable<String> sentinelHosts = 
					Splitter.on(',')
					.trimResults()
					.omitEmptyStrings()
					.split(config.getRedisSentinelHosts());
			jedisPool = new JedisSentinelPool(
					config.getRedisSentinelMasterName(), 
					Sets.newHashSet(sentinelHosts), 
					jedisConfig);
		} else {
			jedisPool = new JedisPool(
					jedisConfig,
					config.getRedisHost(),
					config.getRedisPort());
		}
	}

	public <T> T execute(JedisCallBack jcb) {
		return execute(jcb, 0);
	}
	
	public <T> T execute(JedisCallBack jcb, int index) {
		T t = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			t = jcb.execute(jedis);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return t;
	}
}
