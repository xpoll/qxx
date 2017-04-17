package cn.blmdz.common.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Collection;
import java.util.Objects;

public final class Arguments {
	public static <T extends Collection<?>> boolean isNullOrEmpty(T t) {
		return (isNull(t)) || (isEmpty(t));
	}

	public static boolean isNull(Object o) {
		return null == o;
	}

	public static boolean notNull(Object o) {
		return o != null;
	}

	public static boolean isEmpty(String s) {
		return Strings.isNullOrEmpty(s);
	}

	public static boolean notEmpty(String s) {
		return !isEmpty(s);
	}

	public static <T extends Collection<?>> boolean isEmpty(T t) {
		return t.isEmpty();
	}

	public static <T extends Collection<?>> boolean notEmpty(T t) {
		return notNull(t) && !t.isEmpty();
	}

	public static boolean positive(Number n) {
		return n.doubleValue() > 0.0D;
	}

	public static boolean isPositive(Number n) {
		return n != null && n.doubleValue() > 0.0D;
	}

	public static boolean negative(Number n) {
		return n.doubleValue() < 0.0D;
	}

	public static boolean isNegative(Number n) {
		return n != null && n.doubleValue() < 0.0D;
	}

	public static <T> boolean equalWith(T source, T target) {
		return Objects.equals(source, target);
	}

	public static boolean not(Boolean t) {
		Preconditions.checkArgument(notNull(t));
		return !t;
	}

	public static boolean isDecimal(String str) {
		for (char c : str.toCharArray()) {
			if ((c < '0') || (c > '9')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumberic(String str) {
		int dot = 0;
		for (char c : str.toCharArray()) {
			if ((c == '.') && (dot == 0)) {
				dot = 1;
			} else {
				if (c == '.')
					return false;
				if ((c < '0') || (c > '9'))
					return false;
			}
		}
		return true;
	}

}
