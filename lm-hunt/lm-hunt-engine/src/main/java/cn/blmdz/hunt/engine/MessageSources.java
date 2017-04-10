package cn.blmdz.hunt.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSources implements MessageSource {
	private final MessageSource messageSource;

	@Autowired(required = false)
	public MessageSources(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String get(String code) {
		return this.get(code, new Object[0]);
	}

	public String get(String code, Object... args) {
		return this.messageSource == null ? code : this.messageSource.getMessage(code, args, code, ThreadVars.getLocale());
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return this.messageSource == null ? code : this.messageSource.getMessage(code, args, defaultMessage, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return this.messageSource == null ? code : this.messageSource.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return this.messageSource.getMessage(resolvable, locale);
	}
}
