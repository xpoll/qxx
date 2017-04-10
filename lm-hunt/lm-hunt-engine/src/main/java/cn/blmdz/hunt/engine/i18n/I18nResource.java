package cn.blmdz.hunt.engine.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class I18nResource {
	private static final Logger log = LoggerFactory.getLogger(I18nResource.class);
	private Map<String, Object> resource;
	private Locale locale;

	public Set<String> keys() {
		return this.resource.keySet();
	}

	public String message(String key, Object... args) {
		Object message = this.resource.get(key);
		if (message == null) {
			log.warn("no message found for key: {} and locale: {}", key, this.locale);
			return key;
		} else {
			String messageStr;
			if (message instanceof Boolean && ((Boolean) message).booleanValue()) {
				messageStr = key;
			} else {
				messageStr = message.toString();
			}

			if (args.length == 0) {
				return messageStr;
			} else {
				MessageFormat format = new MessageFormat(messageStr, this.locale);
				return format.format(args);
			}
		}
	}
}
