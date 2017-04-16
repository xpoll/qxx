package cn.blmdz.hunt.design.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import redis.clients.jedis.Jedis;

@Repository
public class ItemTemplateRedisDao {
	@Autowired
	private JedisExecutor jedisExecutor;

	public void createOrUpdate(final long spuId, final String htmlCode) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.set(keyBySpuId(spuId), htmlCode);
			}
		});
	}

	public String findBySpuId(final long spuId) {
		return jedisExecutor.execute(new JedisCallBack<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.get(keyBySpuId(spuId));
			}
		});
	}

	public void delete(final long spuId) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.del(keyBySpuId(spuId));
			}
		});
	}

	private static String keyBySpuId(Long spuId) {
		return "item-template:" + spuId;
	}
}
