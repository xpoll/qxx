package cn.blmdz.hunt.engine.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class RedisFileDao {
	@Autowired
	private JedisExecutor jedisExecutor;

	public void save(final String path, final String content) {
		this.checkSupport();
		this.jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				save(path, content, t);
				t.exec();
			}
		}, 0);
	}

	public void save(String path, String content, Transaction t) {
		this.checkSupport();
		Map<String, String> data = ImmutableMap.of("content", content, "updateAt",
				String.valueOf(System.currentTimeMillis()));
		t.hmset(this.key(path), data);
	}

	public void delete(final String path) {
		this.checkSupport();
		this.jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				delete(path, t);
				t.exec();
			}
		}, 0);
	}

	public void delete(String path, Transaction t) {
		this.checkSupport();
		t.del(this.key(path));
	}

	public Long getUpdateTime(final String path) {
		this.checkSupport();
		return this.jedisExecutor.execute(new JedisCallBack<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				String millisStr = jedis.hget(key(path), "updateAt");
				return Strings.isNullOrEmpty(millisStr) ? null : Long.valueOf(millisStr);
			}
		}, 0);
	}

	public String getContent(final String path) {
		this.checkSupport();
		return this.jedisExecutor.execute(new JedisCallBack<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.hget(key(path), "content");
			}
		}, 0);
	}

	private String key(String path) {
		return "file:" + path;
	}

	private void checkSupport() {
		if (this.jedisExecutor == null) {
			throw new IllegalStateException("jedisExecutor not injected, RedisFileDao can not work");
		}
	}
}
