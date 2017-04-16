package cn.blmdz.hunt.design.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import redis.clients.jedis.Jedis;

@Repository
public class ItemCustomRedisDao {
	@Autowired
	private JedisExecutor jedisExecutor;

	public void createOrUpdate(final long itemId, final String htmlCode) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.set(keyByItemId(itemId), htmlCode);
			}
		});
	}

	public String findById(final long itemId) {
		return jedisExecutor.execute(new JedisCallBack<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.get(keyByItemId(itemId));
			}
		});
	}

	public void delete(final long itemId) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.del(keyByItemId(itemId));
			}
		});
	}

	protected static String keyByItemId(Long itemId) {
		return "item-custom:" + itemId;
	}
}
