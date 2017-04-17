package cn.blmdz.hunt.webc.interceptor;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import cn.blmdz.common.model.InnerCookie;
import cn.blmdz.common.model.InnerCookie.FakeCookie;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.engine.utils.CookieBuilder;

@Component("pampasCookieInterceptor")
public class CookieInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String, String> cookieValues = Maps.newHashMap();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieValues.put(
						cookie.getName(),
						URLDecoder.decode(cookie.getValue(),
								Charsets.UTF_8.name()));
			}
		}

		InnerCookie innerCookie = new InnerCookie(cookieValues);
		UserUtil.putInnerCookie(innerCookie);
		return true;
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		InnerCookie innerCookie = UserUtil.getInnerCookie();
		if (innerCookie != null) {
			for (FakeCookie fakeCookie : innerCookie.getNewCookies()) {
				CookieBuilder cookieBuilder = CookieBuilder
						.from(fakeCookie.getName(), fakeCookie.getValue(),
								fakeCookie.getDomain())
						.path(fakeCookie.getPath()).maxAge(fakeCookie.getAge());
				if (fakeCookie.isHttpOnly()) {
					cookieBuilder.httpOnly();
				}

				response.addCookie(cookieBuilder.build());
			}

			for (FakeCookie fakeCookie : innerCookie.getDelCookies()) {
				CookieBuilder cookieBuilder = CookieBuilder
						.from(fakeCookie.getName(), (String) null,
								fakeCookie.getDomain())
						.path(fakeCookie.getPath()).maxAge(0);
				response.addCookie(cookieBuilder.build());
			}

			UserUtil.clearInnerCookie();
		}
	}
}
