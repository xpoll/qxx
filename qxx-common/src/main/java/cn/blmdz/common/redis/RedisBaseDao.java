package cn.blmdz.common.redis;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.serialize.StringHashMapper;
import cn.blmdz.common.util.KeyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public abstract class RedisBaseDao<T> {

    public final StringHashMapper<T> stringHashMapper;
    protected final JedisExecutor jedisExecutor;
    protected final Class<T> entityClass;
    protected final int index;

	@SuppressWarnings("unchecked")
	public RedisBaseDao(JedisExecutor jedisExecutor, Integer index) {
        this.jedisExecutor = jedisExecutor;
        this.index = index == null ? 0 : index.intValue();
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        stringHashMapper = new StringHashMapper<T>(entityClass);
    }

    public List<T> findByIds(Iterable<String> ids) {
        return findByKeys(ids, new Function<String, String>() {
        	@Override
            public String apply(String id) {
                return KeyUtils.entityId(entityClass, id);
            }
        });
    }

    public List<T> findByKeys(final Iterable<String> keys, final Function<String, String> keyGen) {
        if(Iterables.isEmpty(keys)) {
            return Collections.emptyList();
        } else {
            List<Response<Map<String, String>>> result = jedisExecutor.execute(new JedisCallBack<List<Response<Map<String, String>>>>() {
            	@Override
                public List<Response<Map<String, String>>> execute(Jedis jedis) {
                	List<Response<Map<String, String>>> result = Lists.newArrayListWithCapacity(Iterables.size(keys));
                    Pipeline p = jedis.pipelined();
                    for (String key : keys) {
                    	result.add(p.hgetAll(keyGen.apply(key)));
					}
                    p.sync();
                    return result;
                }
            }, index);
            List<T> entities = Lists.newArrayListWithCapacity(result.size());
            for (Response<Map<String, String>> t : result) {
            	entities.add(this.stringHashMapper.fromHash(t.get()));
			}
            return entities;
        }
    }

    protected T findByKey(final Long id) {
        Map<String,String> hash = jedisExecutor.execute(new JedisCallBack<Map<String, String>>() {
            @Override
            public Map<String, String> execute(Jedis jedis) {
                return jedis.hgetAll(KeyUtils.entityId(entityClass,id));
            }
        }, index);
        return stringHashMapper.fromHash(hash);
    }

    protected T findByKey(final String key) {
        Map<String, String> hash = jedisExecutor.execute(new JedisCallBack<Map<String, String>>() {
        	@Override
            public Map<String, String> execute(Jedis jedis) {
                return jedis.hgetAll(KeyUtils.entityId(entityClass, key));
            }
        }, index);
        return this.stringHashMapper.fromHash(hash);
    }

    public Long newId(){
        return jedisExecutor.execute(new JedisCallBack<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.incr(KeyUtils.entityCount(entityClass));
            }
        }, index);
    }
}
