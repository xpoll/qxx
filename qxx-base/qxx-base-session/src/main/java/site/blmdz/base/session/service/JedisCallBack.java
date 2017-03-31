package site.blmdz.base.session.service;

import redis.clients.jedis.Jedis;

/**
 * JedisCallBack
 * redis 回调机制
 * @author lm
 * @date 2016年11月1日 上午12:33:21
 */
public interface JedisCallBack {
	<T> T execute(Jedis jedis);
}
