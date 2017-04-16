package cn.blmdz.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

public class JedisExecutor {
    private Pool<Jedis> jedisPool;

    public JedisExecutor(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T execute(JedisCallBack<T> jedisCallBack) throws JedisException {
        return this.execute(jedisCallBack, 0);
    }

    public <T> T execute(JedisCallBack<T> jedisCallBack, int dbIndex) throws JedisException {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            return jedisCallBack.execute(jedis);
        } finally {
        	if (jedis != null) jedis.close();
        }

    }

    public Pool<Jedis> getJedisPool() {
        return this.jedisPool;
    }
    
    public interface JedisCallBack<T> {
    	T execute(Jedis jedis);
    }

    public void execute(JedisCallBackNoResult jedisAction) throws JedisException {
        this.execute(jedisAction, 0);
    }

    public void execute(JedisCallBackNoResult jedisAction, int dbIndex) throws JedisException {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            jedisAction.execute(jedis);
        } finally {
        	if (jedis != null) jedis.close();
        }

    }

    public interface JedisCallBackNoResult {
        void execute(Jedis jedis);
    }

}
