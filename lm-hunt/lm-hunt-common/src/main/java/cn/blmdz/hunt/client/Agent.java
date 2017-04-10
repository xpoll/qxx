package cn.blmdz.hunt.client;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Agent {
	LinkedHashMap<String, ParamInfo> getParamsInfo(String key);

	WrapResp call(String key, Map<String, Object> params, Map<String, Object> context);

	@Getter
	@AllArgsConstructor
	public static class ParamInfo implements Serializable {
		private static final long serialVersionUID = -3755000483102791609L;
		private boolean isOptional;
		private String className;
	}
}