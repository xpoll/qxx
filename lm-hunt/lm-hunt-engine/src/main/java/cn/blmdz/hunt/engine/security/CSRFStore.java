package cn.blmdz.hunt.engine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import redis.clients.jedis.Jedis;

@Component
public class CSRFStore {
	@Autowired
	private JedisExecutor jedisExecutor;
	private static final int EXPIRE_TIME = 300;

	public boolean checkAndRemoveToken(final String unid, final String token) {
		return jedisExecutor.execute(new JedisCallBack<Boolean>() {
			@Override
			public Boolean execute(Jedis jedis) {
				return Boolean.valueOf(jedis.srem(getKeyByUnid(unid), new String[] { token }).longValue() > 0L);
			}
		}).booleanValue();
	}

	public void addToken(final String unid, final String... tokens) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				jedis.sadd(getKeyByUnid(unid), tokens);
				jedis.expire(getKeyByUnid(unid), EXPIRE_TIME);
			}
		});
	}

	private String getKeyByUnid(String unid) {
		return "qxx:csrf-tokens:" + unid;
	}
}