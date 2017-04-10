package cn.blmdz.hunt.webc.resolver;

import java.io.IOException;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.google.common.base.Preconditions;

import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;

public class HandlebarsViewResolver extends AbstractTemplateViewResolver {
	public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
	private HandlebarsEngine handlebarsEngine;

	public HandlebarsViewResolver(HandlebarsEngine handlebarsEngine) {
		Preconditions.checkNotNull(handlebarsEngine, "The handlebarsEngine object is required.");
		this.handlebarsEngine = handlebarsEngine;
		setViewClass(HandlebarsView.class);
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	public void setPrefix(String prefix) {
		throw new UnsupportedOperationException("Use " + ServletContextTemplateLoader.class.getName() + "#setPrefix");
	}

	public void setSuffix(String suffix) {
		throw new UnsupportedOperationException("Use " + ServletContextTemplateLoader.class.getName() + "#setSuffix");
	}

	public HandlebarsViewResolver() {
		throw new UnsupportedOperationException("operation not supported");
	}

	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		return this.configure((HandlebarsView) super.buildView(viewName));
	}

	protected AbstractUrlBasedView configure(HandlebarsView view)
			throws IOException {
		String url = view.getUrl();
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		view.init(handlebarsEngine, url);
		return view;
	}

	protected Class<?> requiredViewClass() {
		return HandlebarsView.class;
	}
}
