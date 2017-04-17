package cn.blmdz.common.util;

import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.model.InnerCookie;

public final class UserUtil {
	private static ThreadLocal<BaseUser> user = new ThreadLocal<BaseUser>();

	private static ThreadLocal<InnerCookie> cookies = new ThreadLocal<InnerCookie>();

	public static void putCurrentUser(BaseUser baseUser) {
		user.set(baseUser);
	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseUser> T getCurrentUser() {
		return (T) user.get();
	}

	public static void clearCurrentUser() {
		user.remove();
	}

	public static Long getUserId() {
		BaseUser baseUser = getCurrentUser();
		if (baseUser != null) {
			return baseUser.getId();
		}
		return null;
	}

	public static void putInnerCookie(InnerCookie innerCookie) {
		cookies.set(innerCookie);
	}

	public static InnerCookie getInnerCookie() {
		return cookies.get();
	}

	public static void clearInnerCookie() {
		cookies.remove();
	}
}