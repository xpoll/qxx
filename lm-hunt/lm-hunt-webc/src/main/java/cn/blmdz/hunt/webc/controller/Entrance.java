package cn.blmdz.hunt.webc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;

import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.request.AssetsHandler;
import cn.blmdz.hunt.engine.request.MappingHandler;
import cn.blmdz.hunt.engine.request.ViewRender;
import cn.blmdz.hunt.engine.utils.Domains;

@Controller
public class Entrance {
	@Autowired
	private ViewRender viewRender;
	@Autowired
	private AssetsHandler assetsHandler;
	@Autowired
	private MappingHandler mappingHandler;

	@RequestMapping
	public void doRequest(HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> context) {
		String domain = Domains.getDomainFromRequest(request);

		String path = request.getRequestURI().substring(request.getContextPath().length() + 1);
		
		if (Strings.isNullOrEmpty(path)) {
			path = "index";
		}

		context = prepareContext(request, context);
		
		boolean isAssets = assetsHandler.handle(path, response);
		if (!isAssets) {
			boolean hasMapping = mappingHandler.handle(path, request, response, context);
			if (!hasMapping) {
				viewRender.view(domain, path, request, response, context);
			}
		}
	}

	private Map<String, Object> prepareContext(HttpServletRequest request, Map<String, Object> context) {
		if (request != null) {
			for (String name : request.getParameterMap().keySet()) {
				context.put(name, request.getParameter(name));
			}
		}

		context.put(RenderConstants.USER, UserUtil.getCurrentUser());
		return context;
	}
}
