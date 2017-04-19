package cn.blmdz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.entity.QxxImage;

@Repository
public class ImageDao extends RedisBaseDao<QxxImage> {

	private final static int select = 1;
	
	@Autowired
	public ImageDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

}