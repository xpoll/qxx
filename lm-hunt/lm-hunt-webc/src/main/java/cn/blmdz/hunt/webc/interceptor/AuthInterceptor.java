package cn.blmdz.hunt.webc.interceptor;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.exception.UnAuthorize401Exception;
import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.config.model.Auths;
import cn.blmdz.hunt.engine.config.model.FrontConfig;
import cn.blmdz.hunt.engine.config.model.HttpMethod;
import cn.blmdz.hunt.engine.config.model.ProtectedAuth;
import cn.blmdz.hunt.engine.config.model.WhiteAuth;
import cn.blmdz.hunt.engine.service.ConfigService;

@Component("pampasAuthInterceptor")
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private ConfigService configService;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI().substring(
				request.getContextPath().length());
		BaseUser user = UserUtil.getCurrentUser();
		Auths auths = configService.getFrontConfig(ThreadVars.getAppKey()).getAuths();
		if (auths == null)
			return true;
		
		HttpMethod method = HttpMethod.valueOf(request.getMethod().toUpperCase());
		if (auths.getWhiteList() != null) {
			for (WhiteAuth whiteAuth : auths.getWhiteList()) {
				if (whiteAuth.getRegexPattern().matcher(requestURI).matches()
						&& (CollectionUtils.isEmpty(whiteAuth.getMethods())
						|| whiteAuth.getMethods().contains(method))) {
					return true;
				}
			}
		}

		boolean inProtectedList = false;
		if (auths.getProtectedList() != null) {
			for (ProtectedAuth protectedAuth : auths.getProtectedList()) {
				if (protectedAuth.getRegexPattern().matcher(requestURI).matches()) {
					inProtectedList = true;
					if (user == null) {
						redirectToLogin(request, response);
						return false;
					}

					if (CollectionUtils.isEmpty(protectedAuth.getTypes())
							&& CollectionUtils.isEmpty(protectedAuth
									.getRoles())) {
						return true;
					}

					if (!CollectionUtils.isEmpty(protectedAuth.getTypes())
							&& typeMatch(protectedAuth.getTypes(), user.getType())) {
						return true;
					}

					if (!CollectionUtils.isEmpty(protectedAuth.getRoles())
							&& roleMatch(protectedAuth.getRoles(),
									user.getRoles())) {
						return true;
					}
				}
			}
		}

		if (inProtectedList) {
			throw new UnAuthorize401Exception("您无权进行此操作");
		} else if (method != HttpMethod.GET && user == null) {
			redirectToLogin(request, response);
			return false;
		} else {
			return true;
		}
	}

	private boolean typeMatch(Set<String> expectedType, String actualType) {
		return actualType != null && expectedType.contains(actualType);
	}

	private boolean roleMatch(Set<String> expectedRoles, List<String> actualRoles) {
		if (actualRoles != null && !actualRoles.isEmpty()) {
			for (String actualRole : actualRoles) {
				if (expectedRoles.contains(actualRole)) {
					return true;
				}
			}

			return false;
		}
		return false;
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (isAjaxRequest(request)) {
			throw new JsonResponseException(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
		} else {
			String currentUrl = request.getRequestURL().toString();
			if (!Strings.isNullOrEmpty(request.getQueryString())) {
				currentUrl = currentUrl + "?" + request.getQueryString();
			}

			FrontConfig frontConfig = configService.getFrontConfig(ThreadVars.getAppKey());
			UriComponents uriComponents = UriComponentsBuilder.fromUriString(
					frontConfig.getCurrentHrefs(ThreadVars.getHost())
							.get("login") + "?target={target}").build();
			URI uri = uriComponents.expand(new Object[] { currentUrl }).encode().toUri();
			response.sendRedirect(uri.toString());
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return Objects.equal(request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}
}
