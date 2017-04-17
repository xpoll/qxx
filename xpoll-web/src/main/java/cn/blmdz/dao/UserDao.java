package cn.blmdz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.model.QxxUser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class UserDao extends RedisBaseDao<QxxUser> {

	private final static int index = 1;
	
	@Autowired
	public UserDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, index);
	}
	
	public Long create(final QxxUser user) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				create(t, user);
				t.exec();
			}
		}, index);
		return user.getId();
	}
	
	public Long findIdByOwner(final String owner) {
		if (Strings.isNullOrEmpty(owner)) return null;
		
		return jedisExecutor.execute(new JedisCallBack<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				String id = jedis.hget(indexOwner(), owner);
				return Strings.isNullOrEmpty(id) ? null : Long.valueOf(id);
			}
		}, index);
	}
	
	protected Long create(Transaction t, QxxUser user) {
		user.setId(newId());
		t.hset(indexOwner(), user.getOwner(), String.valueOf(user.getId()));
		t.hmset(KeyUtils.entityId(QxxUser.class, user.getId()), stringHashMapper.toHash(user));
		return user.getId();
	}

	protected static String indexOwner() {
		return KeyUtils.entityIndex(QxxUser.class, "owner");
	}
}
