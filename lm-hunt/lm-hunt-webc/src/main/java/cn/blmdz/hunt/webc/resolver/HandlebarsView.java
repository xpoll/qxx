package cn.blmdz.hunt.webc.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.google.common.base.Preconditions;

import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;

public class HandlebarsView extends AbstractTemplateView {
	private HandlebarsEngine handlebarsEngine;
	private String path;

	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		model.put(RenderConstants.PATH, path);
		response.getWriter().write(handlebarsEngine.execPath(path, model, false));
	}

	void init(HandlebarsEngine handlebarsEngine, String path) {
		Preconditions.checkNotNull(handlebarsEngine, "A handlebarsEngine is required.");
		Preconditions.checkNotNull(path, "A path is required.");
		this.handlebarsEngine = handlebarsEngine;
		this.path = path;
	}
}
