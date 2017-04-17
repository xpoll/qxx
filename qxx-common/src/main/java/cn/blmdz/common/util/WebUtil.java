package cn.blmdz.common.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebUtil.java
 */
public class WebUtil {
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	private static final String[] AGENT_INDEX = new String[] { "MSIE", "Firefox", "Chrome", "Opera", "Safari" };
	private static final Map<String, Pattern> AGENT_PATTERNS = ImmutableMap.of(AGENT_INDEX[0],
			Pattern.compile("MSIE ([\\d.]+)"), AGENT_INDEX[1], Pattern.compile("Firefox/(\\d.+)"), AGENT_INDEX[2],
			Pattern.compile("Chrome/([\\d.]+)"), AGENT_INDEX[3], Pattern.compile("Opera[/\\s]([\\d.]+)"),
			AGENT_INDEX[4], Pattern.compile("Version/([\\d.]+)"));

	private static ThreadLocal<HttpServletRequest> req = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> resp = new ThreadLocal<HttpServletResponse>();

	public static void putRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		req.set(request);
		resp.set(response);
	}

	public static HttpServletRequest getRequest() {
		return req.get();
	}

	public static HttpServletResponse getResponse() {
		return resp.get();
	}

	public static void clear() {
		req.remove();
		resp.remove();
	}

	/**
	 * 获取请求IP
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (!Strings.isNullOrEmpty(ip) && ip.contains(",")) {
			List<String> ips = Splitter.on(",").splitToList(ip);
			return (String) ips.get(0);
		} else {
			return ip;
		}
	}

	public static Cookie findCookie(HttpServletRequest request, String name) {
		if (request != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(name)) {
						return cookie;
					}
				}
			}
		}

		return null;
	}

	public static String findCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = findCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int maxAge) {
		addCookie(request, response, name, value, (String) null, maxAge, false);
	}

	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			String domain, int maxAge, boolean httpOnly) {
		String contextPath = request.getContextPath();
		if (contextPath == null || contextPath.isEmpty()) {
			contextPath = "/";
		}

		addCookie(request, response, name, value, domain, contextPath, maxAge, httpOnly);
	}

	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			String domain, String contextPath, int maxAge, boolean httpOnly) {
		if (request != null && response != null) {
			Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(maxAge);
			cookie.setSecure(request.isSecure());
			if (contextPath != null && !contextPath.isEmpty()) {
				cookie.setPath(contextPath);
			} else {
				cookie.setPath("/");
			}

			if (domain != null && !domain.isEmpty()) {
				cookie.setDomain(domain);
			}

			if (httpOnly) {
				cookie.setHttpOnly(true);
			}

			response.addCookie(cookie);
			logger.debug("Cookie update the sessionID.[name={},value={},maxAge={},httpOnly={},path={},domain={}]",
					new Object[] { cookie.getName(), cookie.getValue(), Integer.valueOf(cookie.getMaxAge()),
							Boolean.valueOf(httpOnly), cookie.getPath(), cookie.getDomain() });
		}

	}

	public static void failureCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String domain, String contextPath) {
		if (request != null && response != null && name != null) {
			addCookie(request, response, name, (String) null, domain, contextPath, 0, true);
		}

	}

	public static void failureCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String domain) {
		String contextPath = request.getContextPath();
		if (contextPath == null || contextPath.isEmpty()) {
			contextPath = "/";
		}

		failureCookie(request, response, name, domain, contextPath);
	}

	public static void failureCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		failureCookie(request, response, name, (String) null);
	}

	public static String getFullRequestUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder(request.getRequestURL().toString());
		String queryString = request.getQueryString();
		if (queryString != null) {
			buff.append("?").append(queryString);
		}

		return buff.toString();
	}

	public static void redirect(HttpServletResponse response, String url, boolean movePermanently) throws IOException {
		if (!movePermanently) {
			response.sendRedirect(url);
		} else {
			response.setStatus(301);
			response.setHeader("Location", url);
		}

	}

	public static WebUtil.UserAgent getUserAgent(String userAgent) {
		if (userAgent != null && !userAgent.isEmpty()) {
			for (String aAGENT_INDEX : AGENT_INDEX) {
				Pattern pattern = (Pattern) AGENT_PATTERNS.get(aAGENT_INDEX);
				Matcher matcher = pattern.matcher(userAgent);
				if (matcher.find()) {
					return new WebUtil.UserAgent(aAGENT_INDEX, matcher.group(1));
				}
			}

			return null;
		} else {
			return null;
		}
	}

	public static WebUtil.UserAgent getUserAgent(HttpServletRequest request) {
		if (request == null) {
			return null;
		} else {
			String userAgentHead = request.getHeader("User-Agent");
			return getUserAgent(userAgentHead);
		}
	}

	public static class UserAgent {
		private String name = "";
		private String version = "";

		public UserAgent(String name, String version) {
			this.name = name;
			this.version = version;
		}

		public String getName() {
			return this.name;
		}

		public String getVersion() {
			return this.version;
		}
	}
}
