package cn.blmdz.hunt.design.util;

import com.google.common.base.Strings;

public enum PagePartScope {
	
	NAIVE, PAGE, SITE, GLOBAL, UNKNOWN;
	
	public static PagePartScope fromString(String scope) {
		if (Strings.isNullOrEmpty(scope))
			return NAIVE;
		try {
			return valueOf(scope.toUpperCase());
		} catch (IllegalArgumentException e) {
		}
		return UNKNOWN;
	}

	public String toString() {
		if (this == NAIVE) {
			return null;
		}
		return super.toString();
	}
}
