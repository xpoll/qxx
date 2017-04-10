package cn.blmdz.hunt.engine.handlebars;

import java.util.Map;

import cn.blmdz.hunt.engine.config.model.Component;

public interface ContextHandler {
	void handle(Component component, Map<String, Object> context);
}
