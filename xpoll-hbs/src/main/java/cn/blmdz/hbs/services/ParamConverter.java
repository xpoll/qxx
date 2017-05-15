package cn.blmdz.hbs.services;

import java.util.Map;

public class ParamConverter {
	public static Object convertParam(String paramName, Class<?> paramClass, Map<String, Object> params,
			boolean isOptional) {
//		if (BaseUser.class.isAssignableFrom(paramClass)) {
//			Object user = UserUtil.getCurrentUser();
//			if (user == null && !isOptional)
//				throw new UserNotLoginException("user not login.");
//			else
//				return user;
//
//		} else if (HttpServletRequest.class.isAssignableFrom(paramClass)) {
//			Object request = WebUtil.getRequest();
//			if (request == null && !isOptional)
//				throw new IllegalStateException("request not inject");
//			else
//				return request;
//
//		} else if (HttpServletResponse.class.isAssignableFrom(paramClass)) {
//			Object response = WebUtil.getResponse();
//			if (response == null && !isOptional)
//				throw new IllegalStateException("response not inject");
//			else
//				return response;
//
//		} else if (paramClass == InnerCookie.class) {
//			return UserUtil.getInnerCookie();
//		} else {
//			Object param = params.get(paramName);
//			return param == null && Map.class.isAssignableFrom(paramClass) ? filterCustomObject(params)
//					: (!paramClass.isPrimitive() ? param : (param == null ? Defaults.defaultValue(paramClass) : param));
//		}
		return null;
	}

//	private static Map<String, String> filterCustomObject(Map<String, Object> params) {
//		Map<String, String> targetParam = Maps.newHashMapWithExpectedSize(params.size());
//
//		for (String key : params.keySet()) {
//			Object value = params.get(key);
//			if (value != null && ParamUtil.isBaseClass(value))
//				targetParam.put(key, String.valueOf(value));
//		}
//
//		return targetParam;
//	}
//
//	public interface UnKnowClass {
//	}
}