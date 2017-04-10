package cn.blmdz.hunt.engine.utils;

import lombok.Getter;

public enum Protocol {
	NONE(""),
	HTTP("http://"),
	HTTPS("https://"),
	SERVLET("resources://"),
	CLASSPATH("classpath:"),
	FILE("file:"),
	ZK("zookeeper://"),
	REDIS("redis:");

	@Getter
	private String prefix;

	private Protocol(String prefix) {
		this.prefix = prefix;
	}

	public static Protocol analyze(String uri) {
		String lowerUri = uri.toLowerCase();
		return lowerUri.startsWith(FILE.prefix) ? FILE : (lowerUri
				.startsWith(HTTP.prefix) ? HTTP : (lowerUri
				.startsWith(HTTPS.prefix) ? HTTPS : (lowerUri
				.startsWith(SERVLET.prefix) ? SERVLET : (lowerUri
				.startsWith(CLASSPATH.prefix) ? CLASSPATH : (lowerUri
				.startsWith(ZK.prefix) ? ZK : (lowerUri
				.startsWith(REDIS.prefix) ? REDIS : NONE))))));
	}

	public static String removeProtocol(String uri) {
		return removeProtocol(uri, analyze(uri));
	}

	public static String removeProtocol(String uri, Protocol protocol) {
		return uri.substring(protocol.prefix.length());
	}

}
