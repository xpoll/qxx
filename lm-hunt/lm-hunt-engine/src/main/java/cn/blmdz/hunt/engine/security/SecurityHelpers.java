package cn.blmdz.hunt.engine.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import cn.blmdz.hunt.engine.handlebars.AbstractHelpers;

@Component
public class SecurityHelpers extends AbstractHelpers {
	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("csrfToken", new Helper<Object>() {
			public CharSequence apply(Object s, Options options)
					throws IOException {
				return CSRFUtil.genToken();
			}
		});
	}
}