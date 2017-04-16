package cn.blmdz.session.serialize;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import cn.blmdz.session.exception.SerializeException;
import cn.blmdz.session.service.Serialize;

/**
 * Json序列化
 * @author lm
 * @date 2016年11月4日 下午10:36:52
 */
public class JsonSerialize implements Serialize {

	private static final Logger log = LoggerFactory.getLogger(JsonSerialize.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	private JavaType javaType;
	
	public JsonSerialize() {
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Object.class});
	}
	
	@Override
	public String serialize(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			log.error("failed to serialize http session {} to json, case:{}", o, Throwables.getStackTraceAsString(e));
			throw new SerializeException("failed to serialize http session to json", e);
		}
	}

	@Override
	public Map<String, Object> deserialize(String o) {
		
		try {
			return objectMapper.readValue(o, javaType);
		} catch (IOException e) {
			log.error("failed to deserialize string {} to http session, case:{}", o, Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new SerializeException("failed to deserialize string to http session", e);
		}
	}

}
