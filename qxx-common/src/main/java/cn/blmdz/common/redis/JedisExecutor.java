package cn.blmdz.common.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

@AllArgsConstructor
public class JedisExecutor {
	
	@Getter
    private Pool<Jedis> jedisPool;

    public interface JedisCallBack<T> {
    	T execute(Jedis jedis);
    }

    public interface JedisCallBackNoResult {
        void execute(Jedis jedis);
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
}
