package cn.blmdz.hunt.engine.handlebars;

import com.github.jknack.handlebars.Helper;
import com.google.common.collect.Maps;

import lombok.Getter;

import java.util.Map;
import javax.annotation.PostConstruct;

public abstract class AbstractHelpers {

	@Getter
	private Map<String, Helper<?>> helpers = Maps.newHashMap();

	@PostConstruct
	public void init() {
		this.fillHelpers(this.helpers);
	}

	protected abstract void fillHelpers(Map<String, Helper<?>> helpers);

}
