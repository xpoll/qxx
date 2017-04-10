package cn.blmdz.hunt.engine.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
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
}
