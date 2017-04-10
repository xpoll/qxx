package cn.blmdz.hunt.engine.i18n;

import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import cn.blmdz.hunt.common.util.Splitters;

public class LocaleTag {
	private String language;
	private String country;
	private String variant;
	private String localeString;

	public LocaleTag(String localeStr) {
		this.localeString = localeStr;
		List<String> locales = Splitters.UNDERSCORE.splitToList(localeStr);
		this.language = (String) locales.get(0);
		if (locales.size() > 1) {
			this.country = (String) locales.get(1);
			if (locales.size() > 2) {
				this.variant = (String) locales.get(2);
			}
		}

	}

	public int match(Locale locale) {
		return this.match(locale.toString());
	}

	public int match(String localeStr) {
		int matchCount = 0;
		List<String> locales = Splitters.UNDERSCORE.splitToList(localeStr);
		if (((String) locales.get(0)).equalsIgnoreCase(this.language)) {
			++matchCount;
			if (locales.size() > 1 && ((String) locales.get(1)).equalsIgnoreCase(this.country)) {
				++matchCount;
				if (locales.size() > 2 && ((String) locales.get(2)).equalsIgnoreCase(this.variant)) {
					++matchCount;
				}
			}

			return matchCount;
		} else {
			return matchCount;
		}
	}

	public Locale toLocale() {
		return StringUtils.parseLocaleString(this.localeString);
	}

	public String toString() {
		return this.localeString;
	}

	public int size() {
		return this.country == null ? 1 : (this.variant == null ? 2 : 3);
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVariant() {
		return this.variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}
}
