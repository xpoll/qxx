package cn.blmdz.hbs.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.convert.support.DefaultConversionService;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ParamUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static JavaType mapType = mapper.getTypeFactory().constructParametrizedType(HashMap.class, HashMap.class,
			new Class[] { String.class, Object.class });
	private static Map<String, Class<?>> primitiveClassMap = Maps.newHashMap();
	private static DefaultConversionService conversionService = new DefaultConversionService();

	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		primitiveClassMap.put("int", Integer.TYPE);
		primitiveClassMap.put("long", Long.TYPE);
		primitiveClassMap.put("short", Short.TYPE);
		primitiveClassMap.put("byte", Byte.TYPE);
		primitiveClassMap.put("char", Character.TYPE);
		primitiveClassMap.put("boolean", Boolean.TYPE);
		primitiveClassMap.put("float", Float.TYPE);
		primitiveClassMap.put("double", Double.TYPE);
	}

	public static Class<?> getPrimitiveClass(String className) {
		return primitiveClassMap.get(className);
	}

	public static boolean isBaseClass(Object obj) {
		return obj instanceof Class
				? ((Class<?>) obj).isPrimitive() || obj == String.class || obj == Integer.class || obj == Long.class
						|| obj == Short.class || obj == Byte.class || obj == Character.class || obj == Boolean.class
						|| obj == Float.class || obj == Double.class
				: obj instanceof String || obj instanceof Integer || obj instanceof Long || obj instanceof Short
						|| obj instanceof Byte || obj instanceof Character || obj instanceof Boolean
						|| obj instanceof Float || obj instanceof Double;
	}

	public static Object convert(Object param, ParamUtil.ParamInfo paramInfo, Map<String, Object> context) {
		if (param == null && paramInfo.isOptional()) {
			return Optional.absent();
		} else {
			Object result = _convert(param, paramInfo.getClazz(), paramInfo.getJavaType(), context);
			return paramInfo.isOptional() ? Optional.fromNullable(result) : result;
		}
	}

	private static Object _convert(Object param, Class<?> clazz, JavaType javaType, Map<String, Object> context) {
		if (isBaseClass(clazz)) {
			return conversionService.convert(param, clazz);
		} else if (param != null && clazz.isAssignableFrom(param.getClass())) {
			return param;
		} else if (param != null && param instanceof String) {
			try {
				return javaType != null ? mapper.readValue(param.toString(), javaType)
						: mapper.readValue(param.toString(), clazz);
			} catch (IOException var5) {
				throw new RuntimeException("json 2 obj mapping error: " + param, var5);
			}
		} else {
			return context != null && !context.isEmpty() ? mapper.convertValue(context, clazz) : null;
		}
	}

	public static Object convertResult(Object result) {
		if (result == null) {
			return null;
		} else if (isBaseClass(result)) {
			return result;
		} else {
			Map<String, Object> convertedResult = mapper.convertValue(new ParamUtil.ConvertWrap(result), mapType);
			return convertedResult.get("obj");
		}
	}

	public static ParamUtil.MethodInfo getMethodInfo(Object bean, Method method) {
		Export methodExport = (Export) method.getAnnotation(Export.class);
		if (methodExport == null) {
			return null;
		} else {
			ParamUtil.MethodInfo methodInfo = new ParamUtil.MethodInfo();
			methodInfo.bean = bean;
			methodInfo.method = method;
			Class<?>[] paramClasses = method.getParameterTypes();
			Type[] paramTypes = method.getGenericParameterTypes();
			String[] paramNames = methodExport.value().length > 0 ? methodExport.value() : methodExport.paramNames();
			if (paramClasses.length != paramNames.length) {
				throw new IllegalArgumentException("param count not same: " + method.toString());
			} else {
				for (int i = 0; i < paramClasses.length; ++i) {
					Class<?> paramClass = paramClasses[i];
					Type paramType = paramTypes[i];
					boolean isOptional = false;
					if (paramClass.isAssignableFrom(Optional.class)) {
						ParameterizedType tType = (ParameterizedType) paramType;
						paramType = tType.getActualTypeArguments()[0];
						paramClass = (Class<?>) paramType;
						isOptional = true;
					}

					JavaType javaType = constructJavaType(paramType);
					methodInfo.params.put(paramNames[i], new ParamUtil.ParamInfo(paramClass, javaType, isOptional));
				}

				return methodInfo;
			}
		}
	}

	private static JavaType constructJavaType(Type type) {
		if (!(type instanceof ParameterizedType)) {
			return mapper.getTypeFactory().constructType(type);
		} else {
			ParameterizedType ptype = (ParameterizedType) type;
			Type[] typeArgs = ptype.getActualTypeArguments();
			JavaType[] javaTypes = new JavaType[typeArgs.length];

			for (int i = 0; i < typeArgs.length; ++i) {
				javaTypes[i] = constructJavaType(typeArgs[i]);
			}

			return mapper.getTypeFactory().constructParametrizedType((Class<?>)ptype.getRawType(), (Class<?>)ptype.getRawType(), javaTypes);
		}
	}

    @Getter
    @Setter
    @AllArgsConstructor
	private static class ConvertWrap implements Serializable {
		private static final long serialVersionUID = 0;
		private Object obj;
	}

    @Getter
    @Setter
	public static class MethodInfo {
        private Object bean;
        private Method method;
        private LinkedHashMap<String, ParamInfo> params = Maps.newLinkedHashMap();
	}

    @Getter
    @AllArgsConstructor
	public static class ParamInfo {
    	private Class<?> clazz;
    	private JavaType javaType;
		private boolean isOptional;

		public ParamInfo(Class<?> clazz, JavaType javaType) {
			this(clazz, javaType, false);
		}
	}
}
