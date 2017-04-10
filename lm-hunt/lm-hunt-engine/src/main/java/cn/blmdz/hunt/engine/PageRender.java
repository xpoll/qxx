package cn.blmdz.hunt.engine;

import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.exception.NotFound404Exception;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PageRender {
	@Autowired
	protected HandlebarsEngine handlebarsEngine;

	public String render(String domain, String path, Map<String, Object> context) {
		return this.naiveRender(path, context);
	}

	public String naiveRender(String path, Map<String, Object> context) throws NotFound404Exception {
		return this.naiveRender(path, null, context);
	}

	public String naiveRender(String templatePath, String shownPath, Map<String, Object> context) throws NotFound404Exception {
		context.put(RenderConstants.PATH, MoreObjects.firstNonNull(Strings.emptyToNull(shownPath), templatePath));

		try {
			return this.handlebarsEngine.execPath(templatePath, context, false);
		} catch (FileNotFoundException var5) {
			log.error("failed to find page {},cause:{}", new Object[] { templatePath, var5.getMessage(), var5 });
			throw new NotFound404Exception("page not found");
		}
	}
}
