package cn.blmdz.hunt.webc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Iterables;

import cn.blmdz.hunt.common.exception.JsonResponseException;
import cn.blmdz.hunt.engine.security.CSRFCheck;
import cn.blmdz.hunt.engine.security.CSRFHelper;
import cn.blmdz.hunt.engine.security.CSRFUtil;

@Component("pampasCSRFCheckInterceptor")
public class CSRFCheckInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private CSRFHelper csrfHelper;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		} else {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean needCheck = handlerMethod.getMethod().isAnnotationPresent(
					CSRFCheck.class);
			if (needCheck) {
				if (csrfHelper.check(request)) {
					return true;
				} else {
					throw new JsonResponseException(403, "Duplicate request");
				}
			} else {
				return true;
			}
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		List<String> csrfTokens = CSRFUtil.getAndClearThreadToken();
		if (csrfTokens != null && !csrfTokens.isEmpty()) {
			csrfHelper.save(request, (String[]) Iterables.toArray(csrfTokens, String.class));
		}
	}
}
