package cn.blmdz.common.redis;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
    protected final int select;

	@SuppressWarnings("unchecked")
	public RedisBaseDao(JedisExecutor jedisExecutor, Integer select) {
        this.jedisExecutor = jedisExecutor;
        this.select = select == null ? 0 : select.intValue();
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        stringHashMapper = new StringHashMapper<T>(entityClass);
    }

    /**
     * 新建ID
     * @return
     */
    public Long newId(){
        return jedisExecutor.execute(new JedisCallBack<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.incr(KeyUtils.entityCount(entityClass));
            }
        }, select);
    }

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    public T findById(final Long id) {
    	return findByKey(KeyUtils.entityId(entityClass,id));
    }
    
    /**
     * 根据IDs查找
     * @param ids
     * @return
     */
    public List<T> findByIds(final Iterable<Long> ids) {
    	Set<String> keys = Sets.newHashSet();
    	for (Long id : ids) {
    		keys.add(String.valueOf(id));
		}
    	return findByKeys(keys);
    }
    
	/**
	 * 序列key
	 * @param index
	 * @return
	 */
	protected String index(String index) {
		return KeyUtils.entityIndex(entityClass, index);
	}
	
	/**
	 * 根据序列获取对象
	 * @param key
	 * @param index
	 * @return
	 */
	protected T findIdIndex(final String key, final String index) {
		if (Strings.isNullOrEmpty(key)) return null;
		
		Map<String, String> hash = jedisExecutor.execute(new JedisCallBack<Map<String, String>>() {
			@Override
			public Map<String, String> execute(Jedis jedis) {
				String id = jedis.hget(index, key);
				if (Strings.isNullOrEmpty(id)) return null;
				return jedis.hgetAll(KeyUtils.entityId(entityClass,id));
			}
		}, select);

        return stringHashMapper.fromHash(hash);
	}

    protected T findById(final String key) {
    	return findByKey(KeyUtils.entityId(entityClass,key));
    }
    
    private T findByKey(final String key) {
        Map<String, String> hash = jedisExecutor.execute(new JedisCallBack<Map<String, String>>() {
        	@Override
            public Map<String, String> execute(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        }, select);
        return this.stringHashMapper.fromHash(hash);
    }

    protected List<T> findByKeys(final Iterable<String> keys) {
        if(Iterables.isEmpty(keys)) {
            return Collections.emptyList();
        } else {
            List<Response<Map<String, String>>> result = jedisExecutor.execute(new JedisCallBack<List<Response<Map<String, String>>>>() {
            	@Override
                public List<Response<Map<String, String>>> execute(Jedis jedis) {
                	List<Response<Map<String, String>>> result = Lists.newArrayListWithCapacity(Iterables.size(keys));
                    Pipeline p = jedis.pipelined();
                    for (String key : keys) {
                    	result.add(p.hgetAll(KeyUtils.entityId(entityClass, key)));
					}
                    p.sync();
                    return result;
                }
            }, select);
            List<T> entities = Lists.newArrayListWithCapacity(result.size());
            for (Response<Map<String, String>> t : result) {
            	entities.add(this.stringHashMapper.fromHash(t.get()));
			}
            return entities;
        }
    }
}
