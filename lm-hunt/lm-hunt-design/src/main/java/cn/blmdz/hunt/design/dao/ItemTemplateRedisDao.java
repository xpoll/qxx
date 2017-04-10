package cn.blmdz.hunt.design.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import redis.clients.jedis.Jedis;

@Repository
public class ItemTemplateRedisDao {
	private final JedisTemplate jedisTemplate;

	@Autowired
	public ItemTemplateRedisDao(
			@Qualifier("pampasJedisTemplate") JedisTemplate jedisTemplate) {
		this.jedisTemplate = jedisTemplate;
	}

	public void createOrUpdate(final long spuId, final String htmlCode) {
		jedisTemplate.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.set(keyBySpuId(Long.valueOf(spuId)), htmlCode);
			}
		});
	}

	public String findBySpuId(final long spuId) {
		return jedisTemplate.execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(keyBySpuId(Long.valueOf(spuId)));
			}
		});
	}

	public void delete(final long spuId) {
		jedisTemplate.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.del(keyBySpuId(Long.valueOf(spuId)));
			}
		});
	}

	public static String keyBySpuId(Long spuId) {
		return "item-template:" + spuId;
	}
}
