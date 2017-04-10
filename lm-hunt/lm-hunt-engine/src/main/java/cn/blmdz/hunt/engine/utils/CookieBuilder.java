package cn.blmdz.hunt.engine.utils;

import javax.servlet.http.Cookie;

public class CookieBuilder {
	private final String name;
	private final String value;
	private final String domain;
	private String path = "/";
	private boolean httpOnly = false;
	private int maxAge = -1;

	private CookieBuilder(String name, String value, String domain, String path, boolean httpOnly, int maxAge) {
		this.name = name;
		this.value = value;
		this.domain = domain;
		this.path = path;
		this.httpOnly = httpOnly;
		this.maxAge = maxAge;
	}

	private CookieBuilder(String name, String value, String domain) {
		this.name = name;
		this.value = value;
		this.domain = domain;
	}

	public CookieBuilder path(String path) {
		this.path = path;
		return this;
	}

	public CookieBuilder httpOnly() {
		this.httpOnly = true;
		return this;
	}

	public CookieBuilder maxAge(int seconds) {
		this.maxAge = seconds;
		return this;
	}

	public static CookieBuilder from(String name, String value, String domain) {
		return new CookieBuilder(name, value, domain);
	}

	public Cookie build() {
		Cookie cookie = new Cookie(name, value);
		if (domain != null)
			cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

}