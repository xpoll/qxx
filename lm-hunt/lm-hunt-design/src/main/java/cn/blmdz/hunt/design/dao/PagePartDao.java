package cn.blmdz.hunt.design.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class PagePartDao {
	private JedisTemplate template;

	@Autowired
	public PagePartDao(@Qualifier("pampasJedisTemplate") JedisTemplate template) {
		this.template = template;
	}

	public boolean isExists(final String app, final String key, final boolean isRelease) {
		return template.execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.exists(PagePartDao.key(app, key, isRelease));
			}
		}).booleanValue();
	}

	public Map<String, String> findByKey(final String app, final String key, final boolean isRelease) {
		return template.execute(new JedisAction<Map<String, String>>() {
			@Override
			public Map<String, String> action(Jedis jedis) {
				return jedis.hgetAll(PagePartDao.key(app, key, isRelease));
			}
		});
	}

	public void put(final String app, final String key, final Map<String, String> parts, final boolean isRelease) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.hmset(PagePartDao.key(app, key, isRelease), parts);
			}
		});
	}

	public void put(final String app, final String key, final String partKey, final String part, final boolean isRelease) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.hset(PagePartDao.key(app, key, isRelease), partKey, part);
			}
		});
	}

	public void replace(final String app, final String key, final Map<String, String> parts, final boolean isRelease) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				t.del(PagePartDao.key(app, key, isRelease));
				if (parts != null && !parts.isEmpty()) {
					t.hmset(PagePartDao.key(app, key, isRelease), parts);
				}

				t.exec();
			}
		});
	}

	public void delete(final String app, final String key) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				delete(app, key, t);
				t.exec();
			}
		});
	}

	public void delete(String app, String key, Transaction t) {
		t.del(key(app, key, true));
		t.del(key(app, key, false));
	}

	public static String key(String app, String key, boolean isRelease) {
		return isRelease ? "app:" + app + ":page-part-release:" + key : "app:"
				+ app + ":page-part:" + key;
	}
}
