package cn.blmdz.hunt.design.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class PagePartDao {
	@Autowired
	private JedisExecutor jedisExecutor;

	public boolean isExists(final String app, final String key, final boolean isRelease) {
		return jedisExecutor.execute(new JedisCallBack<Boolean>() {
			@Override
			public Boolean execute(Jedis jedis) {
				return jedis.exists(key(app, key, isRelease));
			}
		}, 0).booleanValue();
	}

	public Map<String, String> findByKey(final String app, final String key, final boolean isRelease) {
		return jedisExecutor.execute(new JedisCallBack<Map<String, String>>() {
			@Override
			public Map<String, String> execute(Jedis jedis) {
				return jedis.hgetAll(key(app, key, isRelease));
			}
		}, 0);
	}

	public void put(final String app, final String key, final Map<String, String> parts, final boolean isRelease) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.hmset(key(app, key, isRelease), parts);
			}
		}, 0);
	}

	public void put(final String app, final String key, final String partKey, final String part, final boolean isRelease) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.hset(key(app, key, isRelease), partKey, part);
			}
		}, 0);
	}

	public void replace(final String app, final String key, final Map<String, String> parts, final boolean isRelease) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				t.del(key(app, key, isRelease));
				if (parts != null && !parts.isEmpty()) {
					t.hmset(key(app, key, isRelease), parts);
				}

				t.exec();
			}
		}, 0);
	}

	public void delete(final String app, final String key) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				delete(app, key, t);
				t.exec();
			}
		}, 0);
	}

	protected void delete(String app, String key, Transaction t) {
		t.del(key(app, key, true));
		t.del(key(app, key, false));
	}

	protected static String key(String app, String key, boolean isRelease) {
		return isRelease ? "app:" + app + ":page-part-release:" + key : "app:"
				+ app + ":page-part:" + key;
	}
}
