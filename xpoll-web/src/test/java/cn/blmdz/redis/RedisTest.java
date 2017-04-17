package cn.blmdz.redis;

import java.util.Date;

import com.google.common.base.CaseFormat;

import cn.blmdz.common.serialize.StringHashMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

public class RedisTest {

	public static void main(String[] args) {
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
//		b = jedis.append("a", "value");
//		System.out.println(b);
//		b = jedis.incr("seq:count");
//		System.out.println(b);
//		a = jedis.get("v");
//		System.out.println(a);
//		b = jedis.hset("v", "a", "l");
//		System.out.println(b);
		
//		Student student = new Student();
//		student.setId(1L);
//		student.setDate(new Date());
//		student.setName("sba");
//		Student s = null;
//
////		b = jedis.hset("v", "a", "l");
//		a = jedis.hmset("site:student:5", hash.toHash(student));
//		s = hash.fromHash(jedis.hgetAll("site:student:5"));
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(s.toString());

//		t.hset(keyForSitePages(page.getSiteId()), page.getPath(), page.getId().toString());
//		t.hmset(KeyUtils.entityId(Page.class, page.getId().longValue()), stringHashMapper.toHash(page));

		Date date = new Date();
//		List<Student> students = Lists.newArrayList();
//		students.add(new Student("小明", date));
//		students.add(new Student("小黄", date));
//		students.add(new Student("小花", date));
//		students.add(new Student("小刘", date));
//		students.add(new Student("小李", date));
//		students.add(new Student("小张", date));
//		
//		for (Student student : students) {
//			student.setId(newId(Student.class, jedis));
//			jedis.hmset(entityId(Student.class, student.getId()), hash.toHash(student));
//		}
//		jedis.hset("a:a", "b", "1");
//		jedis.hset("a:a", "c", "2");
//		jedis.hget("a:a", "c");
		
//		jedis.hgetAll("site:4:pages");
//		System.out.println(jedis.exists("site:4:pages"));
		Student student = new Student(1L, "abc", date);
		jedis.hset("student:name", "abc", "1");
		jedis.hmset("student:1", hash.toHash(student));
		System.out.println(jedis.exists("student:name"));
		System.out.println(jedis.hget("student:name", "abc"));
		
		jedis.close();
		
	}
	

    public static <T> Long newId(Class<T> entityClass, Jedis jedis) {
    	return jedis.incr(entityCount(entityClass));
    }
	
    public static <T> String entityCount(Class<T> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":count";
    }

    public static <T> String entityId(Class<T> entityClass, long id) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":" + id;
    }
}
