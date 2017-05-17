package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.web.entity.QxxUser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class UserDao extends RedisBaseDao<QxxUser> {

	private final static int select = 1;
	
	@Autowired
	public UserDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}
	
	public Long create(final QxxUser user) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				create(t, user);
				t.exec();
			}
		}, select);
		return user.getId();
	}
	
	public QxxUser findByOwner(final String owner) {
		return findIdIndex(owner, indexOwner());
	}
	
	public void update(final QxxUser user) {
		jedisExecutor.execute(new JedisCallBackNoResult(){
			@Override
			public void execute(Jedis jedis) {
				jedis.hmset(KeyUtils.entityId(QxxUser.class, user.getId()), stringHashMapper.toHash(user));
			}
		}, select);
	}
	
	protected Long create(Transaction t, QxxUser user) {
		user.setId(newId());
		t.hset(indexOwner(), user.getOwner(), String.valueOf(user.getId()));
		t.hmset(KeyUtils.entityId(QxxUser.class, user.getId()), stringHashMapper.toHash(user));
		return user.getId();
	}

	protected String indexOwner() {
		return index("owner");
	}
}
