package site.blmdz.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class R {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.select(1);
		Set<String> set = jedis.keys("afsession*");
		for (String str : set) {
			System.out.println(str + ":" + jedis.get(str));
		}
	}
}
