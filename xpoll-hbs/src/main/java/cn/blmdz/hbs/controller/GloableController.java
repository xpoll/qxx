package cn.blmdz.hbs.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;

import cn.blmdz.hbs.request.AssetsHandler;
import cn.blmdz.hbs.request.ViewRender;
import cn.blmdz.hbs.util.Domains;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@Controller
public class GloableController {
	@Autowired
	private AssetsHandler assetsHandler;
	@Autowired
	private ViewRender viewRender;

	@RequestMapping
	public void controller(HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
		String domain = Domains.getDomainFromRequest(request);
		String path = request.getRequestURI().substring(request.getContextPath().length() + 1);
		String method = request.getMethod().toUpperCase();

		context = prepareContext(request, context);

		System.out.println(method + ": " + domain + ": " + path);

		if (Strings.isNullOrEmpty(path)) {
			path = "index";
		}

		boolean isAssets = assetsHandler.handle(path, response);
		if (!isAssets) {
			viewRender.view(domain, path, request, response, context);
		}
	}

	private Map<String, Object> prepareContext(HttpServletRequest request, Map<String, Object> context) {
		if (request != null) {
			for (String name : request.getParameterMap().keySet()) {
				context.put(name, request.getParameter(name));
			}
		}
		// context.put(RenderConstants.USER, UserUtil.getCurrentUser());
		return context;
	}
}
