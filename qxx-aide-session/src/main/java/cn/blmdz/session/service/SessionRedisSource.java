package cn.blmdz.session.service;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import cn.blmdz.common.exception.SessionException;
import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.common.serialize.JsonSerialize;
import cn.blmdz.session.QxxSession;
import cn.blmdz.session.properties.SessionProperties;
import redis.clients.jedis.Jedis;

public class SessionRedisSource implements SessionDataSource {
	private final static Logger log = LoggerFactory.getLogger(SessionRedisSource.class);
	private volatile JedisExecutor executor;
	private JsonSerialize serialize;
	private int dbIndex;
	
	public SessionRedisSource(JedisExecutor jedisExecutor, SessionProperties properties) {
		dbIndex = properties.getRedisIndex();
		serialize = new JsonSerialize();
		this.executor = jedisExecutor;
	}

	@Override
	public Map<String, Object> findSessionById(String prefix, String id) {
		final String sessionId = prefix + ":" + id;
		try {
			return executor.execute(new JedisCallBack<Map<String, Object>>() {
				@Override
				public Map<String, Object> execute(Jedis jedis) {
					String session = jedis.get(sessionId);
					return Strings.isNullOrEmpty(session)?Collections.<String, Object>emptyMap():serialize.deserialize(session);
				}
			}, dbIndex);
		} catch (Exception e) {
			log.error("failed to find session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
			throw new SessionException("get session failed", e);
		}
	}

	@Override
	public void refreshExpireTime(QxxSession session, int maxInactiveInterval) {
		refreshExpireTime(session.getPrefix(), session.getId(), maxInactiveInterval);
	}

	@Override
	public void refreshExpireTime(String prefix, String id, final int maxInactiveInterval) {
		final String sessionId = prefix + ":" + id;
		try {
			executor.execute(new JedisCallBackNoResult() {
				@Override
				public void execute(Jedis jedis) {
					jedis.expire(sessionId, maxInactiveInterval);
				}
			}, dbIndex);
		} catch (Exception e) {
			log.error("failed to refresh expire time session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
		}
	}

	@Override
	public void deletePhysically(String prefix, String id) {
		final String sessionId = prefix + ":" + id;
		try {
			executor.execute(new JedisCallBackNoResult() {
				@Override
				public void execute(Jedis jedis) {
					jedis.del(sessionId);
				}
			}, dbIndex);
		} catch (Exception e) {
			log.error("failed to delete session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
		}
	}

	@Override
	public boolean save(String prefix, String id, final Map<String, Object> snapshot, final int maxInactiveInterval) {
		final String sessionId = prefix + ":" + id;
		try {
			executor.execute(new JedisCallBackNoResult() {
				@Override
				public void execute(Jedis jedis) {
					if(snapshot.isEmpty())
						jedis.del(sessionId);
					else
						jedis.setex(sessionId, maxInactiveInterval, serialize.serialize(snapshot));
				}
			}, dbIndex);
			return true;
		} catch (Exception e) {
			log.error("failed to save session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
			return false;
		}
	}

	@Override
	public void destroy() {
		if (executor != null)
			executor.getJedisPool().destroy();
	}

}
