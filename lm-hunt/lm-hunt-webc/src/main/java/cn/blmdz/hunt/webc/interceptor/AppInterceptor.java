package cn.blmdz.hunt.webc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.blmdz.common.exception.NotFound404Exception;
import cn.blmdz.common.util.Splitters;
import cn.blmdz.hunt.engine.RequestAttributes;
import cn.blmdz.hunt.engine.SettingHelper;
import cn.blmdz.hunt.engine.SettingHelper.AppResult;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.utils.Domains;

@Component("pampasAppInterceptor")
public class AppInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SettingHelper settingHelper;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		AppResult appResult = settingHelper.findApp(request);
		if (appResult.getApp() == null) {
			throw new NotFound404Exception("app not found");
		} else {
			if (appResult.isFuzzy()) {
				request.setAttribute(RequestAttributes.FUZZY_APP, Boolean.valueOf(true));
			}

			List<String> host = Splitters.COLON.splitToList(request.getHeader("Host"));
			if (host.size() > 1) {
				ThreadVars.setPort(Integer.valueOf((String) host.get(1)));
			}

			ThreadVars.setDomain(Domains.getNaiveDomainFromRequest(request));
			ThreadVars.setApp(appResult.getApp());
			return true;
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		request.removeAttribute(RequestAttributes.FUZZY_APP);
		ThreadVars.clearPort();
		ThreadVars.clearDomain();
		ThreadVars.clearApp();
	}

	public void setSettingHelper(SettingHelper settingHelper) {
		this.settingHelper = settingHelper;
	}
}
