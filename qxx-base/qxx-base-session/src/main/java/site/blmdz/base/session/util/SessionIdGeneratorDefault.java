package site.blmdz.base.session.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import site.blmdz.base.session.service.SessionIdGenerator;

public class SessionIdGeneratorDefault implements SessionIdGenerator {
	private static final Character SEP = Character.valueOf('Z');
	private final String host_ip_md5;
	@Override
	public String generatorId(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder(30);
		String remote_ip_md5 = Hashing.md5().hashString(WebUtil.getClientIpAddr(request), Charsets.UTF_8).toString().substring(0, 8);
		builder.append(remote_ip_md5).append(SEP).append(host_ip_md5).append(SEP).append(Long.toHexString(System.currentTimeMillis())).append(SEP).append(UUID.randomUUID().toString().substring(0, 4));
		return builder.toString();
	}

	public SessionIdGeneratorDefault() {
		String host_ip;
		try {
			host_ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			host_ip = UUID.randomUUID().toString();
		}
		this.host_ip_md5 = Hashing.md5().hashString(host_ip, Charsets.UTF_8).toString().substring(0, 8);
	}
}
