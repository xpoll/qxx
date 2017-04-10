package cn.blmdz.hunt.design.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.common.exception.JsonResponseException;
import cn.blmdz.hunt.common.util.JsonMapper;
import cn.blmdz.hunt.design.controller.ext.ComponentsHook;
import cn.blmdz.hunt.design.service.DesignMetaService;
import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.config.ConfigManager;
import cn.blmdz.hunt.engine.config.model.Component;
import cn.blmdz.hunt.engine.config.model.FrontConfig;

@Controller
@RequestMapping("/api/design/components")
public class Components {
	@Autowired
	private ConfigManager configManager;
	@Autowired
	private DesignMetaService designMetaService;
	@Autowired(required = false)
	private ComponentsHook componentsHook;
	private static final JavaType MAP_JAVA_TYPE = JsonMapper.nonEmptyMapper()
			.createCollectionType(Map.class,
					new Class[] { String.class, Object.class });

	
	@RequestMapping(value = "render", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String render(@RequestParam String app,
			@RequestParam String template,
			@RequestParam(required = false) String contextJson) {
		Map<String, Object> context;
		if (!Strings.isNullOrEmpty(contextJson)
				&& !Objects.equals(contextJson, "\"\"")) {
			context = JsonMapper.nonEmptyMapper().fromJson(contextJson, MAP_JAVA_TYPE);
		} else {
			context = Maps.newHashMap();
		}

		context.put(RenderConstants.DESIGN_MODE, Boolean.valueOf(true));
		String html = designMetaService.renderComponent(app, template, context);
		if (Strings.isNullOrEmpty(html)) {
			throw new JsonResponseException(404, "组件未找到或者渲染出错");
		} else {
			return html;
		}
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> listCategories(@RequestParam String app,
			@RequestParam(required = false) String mode,
			@RequestParam String pagePath) {
		if (componentsHook != null) {
			return componentsHook.listCategories(mode, pagePath);
		} else {
			Set<String> categories = configManager.getFrontConfig(app)
					.getComponentCategoryListMap().keySet();
			Map<String, String> result = Maps.newHashMap();

			for (String category : categories) {
				result.put(category, category);
			}

			return result;
		}
	}
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Component> list(@RequestParam String app, @RequestParam String category) {
		FrontConfig frontConfig = configManager.getFrontConfig(app);
		if(frontConfig != null && frontConfig.getComponentCategoryListMap() != null)
			return frontConfig.getComponentCategoryListMap().get(category);
		return Collections.emptyList();
	}
}
