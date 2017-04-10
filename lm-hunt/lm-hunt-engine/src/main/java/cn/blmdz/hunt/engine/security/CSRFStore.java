package cn.blmdz.hunt.engine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
@Component
public class CSRFStore {
	@Autowired(required = false)
	@Qualifier("pampasJedisTemplate")
	private JedisTemplate jedisTemplate;
	private static final int EXPIRE_TIME = 300;

	public boolean checkAndRemoveToken(final String unid, final String token) {
		checkJedisExist();
		return jedisTemplate.execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return Boolean.valueOf(jedis.srem(getKeyByUnid(unid), new String[] { token }).longValue() > 0L);
			}
		}).booleanValue();
	}

	public void addToken(final String unid, final String... tokens) {
		checkJedisExist();
		jedisTemplate.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.sadd(getKeyByUnid(unid), tokens);
				jedis.expire(getKeyByUnid(unid), EXPIRE_TIME);
			}
		});
	}

	private String getKeyByUnid(String unid) {
		return "pampas:csrf-tokens:" + unid;
	}

	private void checkJedisExist() {
		if (jedisTemplate == null) {
			log.error("Need a JedisTemplate to use CSRF token. Please config [pampas.redis].");
			throw new IllegalStateException("Need a JedisTemplate to use CSRF token. Please config [pampas.redis].");
		}
	}
}