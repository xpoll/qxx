package cn.blmdz.hunt.engine.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

import cn.blmdz.common.util.Joiners;
import cn.blmdz.common.util.Splitters;

public class Domains {
	/**
	 * 移除第一个域 www.mall.com -> mall.com xxx.mall.com.cn -> mall.com.cn
	 */
	public static String removeSubDomain(String domain) {
		List<String> parts = Splitters.DOT.splitToList(domain);
		return parts.size() > 2 ? Joiners.DOT.join(parts.subList(1, parts.size())) : domain;
	}

	public static String getSubDomain(String domain) {
		List<String> parts = Splitters.DOT.splitToList(domain);
		return parts.size() > 2 ? (String) parts.get(0) : "";
	}

	public static String getDomainFromRequest(HttpServletRequest request) {
		String domain = (String) request.getAttribute("DOMAIN");
		return !Strings.isNullOrEmpty(domain) ? domain : getNaiveDomainFromRequest(request);
	}

	public static String getNaiveDomainFromRequest(HttpServletRequest request) {
		return Splitters.COLON.splitToList(request.getHeader("Host")).get(0);
	}
}
