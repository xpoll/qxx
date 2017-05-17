package cn.blmdz.web.interceptors;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.blmdz.common.util.UserUtil;
import cn.blmdz.common.util.WebUtil;
import cn.blmdz.web.entity.QxxUser;
import cn.blmdz.web.model.User;
import cn.blmdz.web.service.UserService;
import cn.blmdz.web.util.UserMaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final LoadingCache<Long, QxxUser> userCache;

	@Autowired
	public LoginInterceptor(final UserService userService) {
		userCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
				.build(new CacheLoader<Long, QxxUser>() {
					@Override
					public QxxUser load(Long userId) throws Exception {
						return userService.findById(userId);
					}
				});
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		WebUtil.putRequestAndResponse(request, response);

		HttpSession session = request.getSession(false);
		if (session != null) {
			Object user_id = session.getAttribute("user_id");
			if (user_id != null) {

				final Long userId = Long.valueOf(user_id.toString());
				QxxUser result = userCache.get(userId);
				if (result == null) {
					log.warn("failed to find user where id={}", userId);
					return false;
				}
				User user = UserMaker.from(result);
				UserUtil.putCurrentUser(user);
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		WebUtil.clear();
		UserUtil.clearCurrentUser();
	}
}
