package cn.blmdz.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static final ObjectMapper JSON_NON_EMPTY_MAPPER = new JsonUtil(JsonInclude.Include.NON_EMPTY).getMapper();
	private final ObjectMapper mapper;

	private JsonUtil(JsonInclude.Include include) {
		this.mapper = new ObjectMapper();

		this.mapper.setSerializationInclusion(include);

		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public ObjectMapper getMapper() {
		return this.mapper;
	}
}