package cn.blmdz.hbs.request;

import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import cn.blmdz.hbs.config.RenderConstants;
import cn.blmdz.hbs.exception.NotFound404Exception;
import cn.blmdz.hbs.hbs.HandlebarsEngine;

@Component
public class PageRender {
	@Autowired
	protected HandlebarsEngine handlebarsEngine;

	public String render(String domain, String path, Map<String, Object> context) {
		return this.naiveRender(path, context);
	}

	public String naiveRender(String path, Map<String, Object> context) {
		return this.naiveRender(path, null, context);
	}

	public String naiveRender(String templatePath, String shownPath, Map<String, Object> context) {
		context.put(RenderConstants.PAGE, MoreObjects.firstNonNull(Strings.emptyToNull(shownPath), templatePath));

		try {
			return this.handlebarsEngine.execPath(templatePath, context, false);
		} catch (FileNotFoundException e) {
			throw new NotFound404Exception("page not found");
		}
	}
}
