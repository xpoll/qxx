package cn.blmdz.hunt.design.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import redis.clients.jedis.Jedis;

@Repository
public class ItemCustomRedisDao {
	private final JedisTemplate jedisTemplate;

	@Autowired
	public ItemCustomRedisDao(@Qualifier("pampasJedisTemplate") JedisTemplate jedisTemplate) {
		this.jedisTemplate = jedisTemplate;
	}

	public void createOrUpdate(final long itemId, final String htmlCode) {
		jedisTemplate.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.set(ItemCustomRedisDao.keyByItemId(Long.valueOf(itemId)),
						htmlCode);
			}
		});
	}

	public String findById(final long itemId) {
		return jedisTemplate.execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(ItemCustomRedisDao.keyByItemId(Long
						.valueOf(itemId)));
			}
		});
	}

	public void delete(final long itemId) {
		jedisTemplate.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.del(ItemCustomRedisDao.keyByItemId(Long.valueOf(itemId)));
			}
		});
	}

	public static String keyByItemId(Long itemId) {
		return "item-custom:" + itemId;
	}
}
