package cn.blmdz.hunt.engine.security;

import java.util.List;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public abstract class CSRFUtil {
	private static ThreadLocal<List<String>> threadToken = new ThreadLocal<List<String>>() {
		protected List<String> initialValue() {
			return Lists.newArrayList();
		}
	};
	private static final String SALT = "this is pampas CSRF token salt";
	private static final Random RANDOM = new Random();

	public static String genToken() {
		String token = Hashing.md5()
				.hashString(RANDOM.nextInt() + String.valueOf(System.currentTimeMillis()) + SALT, Charsets.UTF_8)
				.toString();
		threadToken.get().add(token);
		return token;
	}

	public static List<String> getAndClearThreadToken() {
		List<String> tokens = threadToken.get();
		threadToken.remove();
		return tokens;
	}
}