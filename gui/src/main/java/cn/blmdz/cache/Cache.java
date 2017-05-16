package cn.blmdz.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Cache<K, V> {

	private ConcurrentHashMap<K, V> cacheObjMap = new ConcurrentHashMap<>();

	private DelayQueue<DelayItem<Pair<K, V>>> q = new DelayQueue<DelayItem<Pair<K, V>>>();

	private Thread daemonThread;

	public Cache() {
		Runnable daemonTask = new Runnable() {
			public void run() {
				daemonCheck();
			}
		};
		daemonThread = new Thread(daemonTask);
		daemonThread.setDaemon(true);
		daemonThread.setName("Cache Daemon");
		daemonThread.start();
	}

	private void daemonCheck() {

		for (;;) {
			try {
				DelayItem<Pair<K, V>> delayItem = q.take();
				if (delayItem != null) {
					// 超时对象处理
					Pair<K, V> pair = delayItem.getItem();
					cacheObjMap.remove(pair.first, pair.second); // compare and remove

				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	// 添加缓存对象
	public void put(K key, V value, long time, TimeUnit unit) {
		V oldValue = cacheObjMap.put(key, value);

		if (oldValue != null)
			q.remove(key);

		long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
		q.put(new DelayItem<Pair<K, V>>(new Pair<K, V>(key, value), nanoTime));
	}

	public V get(K key) {
		return cacheObjMap.get(key);
	}

	// 测试入口函数
	public static void main(String[] args) throws Exception {
		Cache<Integer, String> cache = new Cache<Integer, String>();
		cache.put(1, "aaaa", 3, TimeUnit.SECONDS);

		Thread.sleep(1000 * 2);
		{
			String str = cache.get(1);
			System.out.println(str);
		}

		Thread.sleep(1000 * 2);
		{
			String str = cache.get(1);
			System.out.println(str);
		}
	}
}
