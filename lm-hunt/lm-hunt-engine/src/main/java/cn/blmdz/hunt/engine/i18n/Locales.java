package cn.blmdz.hunt.engine.i18n;

import java.util.List;
import java.util.Locale;

import cn.blmdz.hunt.common.util.Splitters;

public class Locales {
	public static LocaleTag bestMatch(Locale locale, List<LocaleTag> localeTags) {
		return bestMatch(locale.toString(), localeTags);
	}

	public static LocaleTag bestMatch(String localeStr, List<LocaleTag> localeTags) {
		List<String> locales = Splitters.UNDERSCORE.splitToList(localeStr);
		LocaleTag matched = null;
		int maxMatchCount = 0;

		for (LocaleTag tag : localeTags) {
			int matchCount = match(locales, tag);
			if (matchCount != 0) {
				if (matchCount == locales.size() && locales.size() == tag.size()) {
					return tag;
				}

				if (matchCount > maxMatchCount) {
					maxMatchCount = matchCount;
					matched = tag;
				}
			}
		}

		return matched;
	}

	private static int match(List<String> locales, LocaleTag localeTag) {
		return !(locales.get(0))
				.equalsIgnoreCase(localeTag.getLanguage())
						? 0
						: (locales.size() > 1 && ((String) locales.get(1)).equalsIgnoreCase(localeTag.getCountry())
								? (locales.size() > 2
										&& ((String) locales.get(2)).equalsIgnoreCase(localeTag.getVariant()) ? 3 : 2)
								: 1);
	}
}
