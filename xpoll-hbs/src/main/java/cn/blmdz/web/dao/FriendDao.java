package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.web.entity.QxxFriend;

@Repository
public class FriendDao extends RedisBaseDao<QxxFriend> {

	private final static int select = 1;
	
	@Autowired
	public FriendDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

}
