package cn.blmdz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.model.Friend;

@Repository
public class FriendDao extends RedisBaseDao<Friend> {

	private final static int index = 1;
	
	@Autowired
	public FriendDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, index);
	}

}
