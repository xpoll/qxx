package cn.blmdz.hunt.webc.interceptor;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.blmdz.hunt.engine.SettingHelper;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.i18n.I18nResourceBundle;

@Component("pampasLocaleJudgeInterceptor")
public class LocaleJudgeInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private I18nResourceBundle i18nResourceBundle;
	@Autowired
	private SettingHelper settingHelper;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (settingHelper.getLocale() != null) {
			ThreadVars.setLocale(settingHelper.getLocale());
			return true;
		} else {
			List<Locale> requestLocales = Collections
					.list(request.getLocales());
			Locale locale = i18nResourceBundle.firstMatchLocale(
					ThreadVars.getApp(), requestLocales);
			ThreadVars.setLocale(locale);
			return true;
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ThreadVars.clearLocale();
	}
}
