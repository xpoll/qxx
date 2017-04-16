package cn.blmdz.redis;

import java.util.Date;

import cn.blmdz.hunt.common.util.StringHashMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

public class RedisTest {

	public static void main(String[] args) {
//		Class<Student> entityClass = (Class<Student>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		StringHashMapper<Student> hash = new StringHashMapper<>(Student.class);
		
		Pool<Jedis> pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 2000, null);
		Jedis jedis = pool.getResource();
		
		jedis.select(2);
		String a = "";
		Long b = 111L;

//		b = jedis.del("v");
//		System.out.println(b);
//		a = jedis.set("a", "adsfsdf");
//		System.out.println(a);
//		a = jedis.get("v");
//		System.out.println(a);
//		b = jedis.hset("v", "a", "l");
//		System.out.println(b);
		
		Student student = new Student();
		student.setId(1L);
		student.setDate(new Date());
		student.setName("sba");
		Student s = null;

		b = jedis.hset("v", "a", "l");
		a = jedis.hmset("bbb", hash.toHash(student));
		s = hash.fromHash(jedis.hgetAll("bbb"));
		System.out.println(a);
		System.out.println(b);
		System.out.println(s.toString());

//		t.hset(keyForSitePages(page.getSiteId()), page.getPath(), page.getId().toString());
//		t.hmset(KeyUtils.entityId(Page.class, page.getId().longValue()), stringHashMapper.toHash(page));
		
		
		
		
		
		
		jedis.close();
		
	}
}
