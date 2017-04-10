package cn.blmdz.hunt.common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.KeyUtils;
import cn.blmdz.hunt.common.util.StringHashMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * redis 基础模板类
 * email:dong_peiji@huateng.com
 * Created by 董培基 on 2016/3/3.
 */
public abstract class RedisBaseDao<T> {

    public final StringHashMapper<T> stringHashMapper;
    protected final JedisTemplate template;
    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
	public RedisBaseDao(JedisTemplate template) {
        this.template = template;
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
            List<Response<Map<String, String>>> result = template.execute(new JedisTemplate.JedisAction<List<Response<Map<String, String>>>>() {
            	@Override
                public List<Response<Map<String, String>>> action(Jedis jedis) {
                	List<Response<Map<String, String>>> result = Lists.newArrayListWithCapacity(Iterables.size(keys));
                    Pipeline p = jedis.pipelined();
                    for (String key : keys) {
                    	result.add(p.hgetAll(keyGen.apply(key)));
					}
                    p.sync();
                    return result;
                }
            });
            List<T> entities = Lists.newArrayListWithCapacity(result.size());
            for (Response<Map<String, String>> t : result) {
            	entities.add(this.stringHashMapper.fromHash(t.get()));
			}
            return entities;
        }
    }

    protected T findByKey(final Long id) {
        Map<String,String> hash = template.execute(new JedisTemplate.JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(KeyUtils.entityId(entityClass,id));
            }
        });
        return stringHashMapper.fromHash(hash);
    }

    protected T findByKey(final String key) {
        Map<String, String> hash = template.execute(new JedisTemplate.JedisAction<Map<String, String>>() {
        	@Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(KeyUtils.entityId(entityClass, key));
            }
        });
        return this.stringHashMapper.fromHash(hash);
    }

    public Long newId(){
        return template.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incr(KeyUtils.entityCount(entityClass));
            }
        });
    }
}
