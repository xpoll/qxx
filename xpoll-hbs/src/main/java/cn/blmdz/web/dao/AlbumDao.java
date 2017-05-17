package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.web.entity.QxxAlbum;

@Repository
public class AlbumDao extends RedisBaseDao<QxxAlbum> {

	private final static int select = 1;
	
	@Autowired
	public AlbumDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

}