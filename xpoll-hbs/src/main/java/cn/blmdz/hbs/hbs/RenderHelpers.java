package cn.blmdz.hbs.hbs;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.collect.Maps;

@Component
public class RenderHelpers extends AbstractHelpers {

	@Autowired
	private HandlebarsEngine handlebarsEngine;

	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("inject", new Helper<String>() {
			@SuppressWarnings("unchecked")
			public CharSequence apply(String compPath, Options options) throws IOException {
				Map<String, Object> tempContext = Maps.newHashMap();
				if (options.context.model() instanceof Map) {
					tempContext.putAll((Map<String, Object>) options.context.model());
				}
				return new SafeString(handlebarsEngine.execPath(compPath, tempContext, true));
			}
		});
	}
}
