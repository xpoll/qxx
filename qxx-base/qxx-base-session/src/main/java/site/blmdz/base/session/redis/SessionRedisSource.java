package site.blmdz.base.session.redis;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import site.blmdz.base.session.BlmdzSession;
import site.blmdz.base.session.exception.SessionException;
import site.blmdz.base.session.serialize.JsonSerialize;
import site.blmdz.base.session.serialize.KryoSerialize;
import site.blmdz.base.session.service.JedisCallBack;
import site.blmdz.base.session.service.Serialize;
import site.blmdz.base.session.service.SessionDataSource;
import site.blmdz.base.session.util.Config;

public class SessionRedisSource implements SessionDataSource {
	private final static Logger log = LoggerFactory.getLogger(SessionRedisSource.class);
	private volatile JedisPoolExecutor executor;
	private Serialize serialize;
	private int dbIndex;
	
	public SessionRedisSource(Config config) {
		dbIndex = config.getRedisIndex().intValue();
		if("binary".equals(config.getSerializeType())) {
			serialize = new KryoSerialize();
		} else {
			serialize = new JsonSerialize();
		}
		JedisPoolConfig jedisConfig = new JedisPoolConfig();
		jedisConfig.setTestOnBorrow(config.getRedisTestOnBorrow());//连接池结用前测试
		jedisConfig.setMaxIdle(config.getRedisMaxIdle());
		jedisConfig.setMaxTotal(config.getRedisMaxTotal());
		executor = new JedisPoolExecutor(
				jedisConfig,
				config.getRedisCluster().booleanValue(),
				config);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> findSessionById(String prefix, String id) {
		final String sessionId = prefix + ":" + id;
		try {
			return executor.execute(new JedisCallBack() {
				@Override
				public <T> T execute(Jedis jedis) {
					String session = jedis.get(sessionId);
					return (T) (Strings.isNullOrEmpty(session)?Collections.emptyMap():serialize.deserialize(session));
				}
			}, dbIndex);
		} catch (Exception e) {
			log.error("failed to find session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
			throw new SessionException("get session failed", e);
		}
	}

	@Override
	public void refreshExpireTime(BlmdzSession session, int maxInactiveInterval) {
		refreshExpireTime(session.getPrefix(), session.getId(), maxInactiveInterval);
	}

	@Override
	public void refreshExpireTime(String prefix, String id, final int maxInactiveInterval) {
		final String sessionId = prefix + ":" + id;
		try {
			executor.execute(new JedisCallBack() {
				@Override
				public <T> T execute(Jedis jedis) {
					jedis.expire(sessionId, maxInactiveInterval);
					return null;
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
			executor.execute(new JedisCallBack() {
				@Override
				public <T> T execute(Jedis jedis) {
					jedis.del(sessionId);
					return null;
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
			executor.execute(new JedisCallBack() {
				@Override
				public <T> T execute(Jedis jedis) {
					if(snapshot.isEmpty())
						jedis.del(sessionId);
					else
						jedis.setex(sessionId, maxInactiveInterval, serialize.serialize(snapshot));
					return null;
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
