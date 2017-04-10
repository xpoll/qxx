package cn.blmdz.hunt.engine.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class RedisFileDao {
	@Autowired(required = false)
	@Qualifier("pampasJedisTemplate")
	private JedisTemplate jedisTemplate;

	public void save(final String path, final String content) {
		this.checkSupport();
		this.jedisTemplate.execute(new JedisActionNoResult() {
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				RedisFileDao.this.save(path, content, t);
				t.exec();
			}
		});
	}

	public void save(String path, String content, Transaction t) {
		this.checkSupport();
		Map<String, String> data = ImmutableMap.of("content", content, "updateAt",
				String.valueOf(System.currentTimeMillis()));
		t.hmset(this.key(path), data);
	}

	public void delete(final String path) {
		this.checkSupport();
		this.jedisTemplate.execute(new JedisActionNoResult() {
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				RedisFileDao.this.delete(path, t);
				t.exec();
			}
		});
	}

	public void delete(String path, Transaction t) {
		this.checkSupport();
		t.del(this.key(path));
	}

	public Long getUpdateTime(final String path) {
		this.checkSupport();
		return this.jedisTemplate.execute(new JedisAction<Long>() {
			public Long action(Jedis jedis) {
				String millisStr = jedis.hget(RedisFileDao.this.key(path), "updateAt");
				return Strings.isNullOrEmpty(millisStr) ? null : Long.valueOf(millisStr);
			}
		});
	}

	public String getContent(final String path) {
		this.checkSupport();
		return this.jedisTemplate.execute(new JedisAction<String>() {
			public String action(Jedis jedis) {
				return jedis.hget(RedisFileDao.this.key(path), "content");
			}
		});
	}

	private String key(String path) {
		return "file:" + path;
	}

	private void checkSupport() {
		if (this.jedisTemplate == null) {
			throw new IllegalStateException("jedisTemplate not injected, RedisFileDao can not work");
		}
	}
}
