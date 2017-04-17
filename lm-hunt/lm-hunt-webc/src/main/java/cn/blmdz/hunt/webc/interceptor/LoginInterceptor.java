package cn.blmdz.hunt.webc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;

import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.common.util.WebUtil;
import cn.blmdz.hunt.engine.utils.LoginInfo;
import cn.blmdz.hunt.webc.extention.UserExt;

@Component("pampasLoginInterceptor")
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserExt userExt;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		WebUtil.putRequestAndResponse(request, response);
		String sessionId = UserUtil.getInnerCookie().get("pms_id");
		if (Strings.isNullOrEmpty(sessionId)) {
			UserUtil.putCurrentUser((BaseUser) null);
			return true;
		} else {
			LoginInfo loginInfo = LoginInfo.fromCookieKey(sessionId);
			BaseUser user = userExt.getUser(ThreadVars.getAppKey(),
					loginInfo.getUserId());
			UserUtil.putCurrentUser(user);
			return true;
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		WebUtil.clear();
		UserUtil.clearCurrentUser();
	}
}
