package cn.blmdz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.entity.QxxImage;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class ImageDao extends RedisBaseDao<QxxImage> {

	private final static int select = 1;
	
	@Autowired
	public ImageDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

	public Long create(final QxxImage image) {
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				create(t, image);
				t.exec();
			}
		}, select);
		return image.getId();
	}
	
	protected Long create(Transaction t, QxxImage image) {
		image.setId(newId());
		t.hset(indexAid(), String.valueOf(image.getAid()), String.valueOf(image.getId()));
		t.hmset(KeyUtils.entityId(QxxImage.class, image.getId()), stringHashMapper.toHash(image));
		return image.getId();
	}
	protected String indexAid() {
		return index("aid");
	}
}