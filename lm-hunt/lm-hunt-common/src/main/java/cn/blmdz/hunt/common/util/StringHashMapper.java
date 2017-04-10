package cn.blmdz.hunt.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;

public class StringHashMapper<T> {
	private final JsonMapper mapper = JsonMapper.nonDefaultMapper();
	private final JavaType userType;
	private final JavaType mapType;

	public StringHashMapper(Class<T> type) {
		this.userType = this.mapper.createCollectionType(HashMap.class, String.class, String.class);
		this.mapType = this.mapper.getMapper().constructType(type);
	}

	@SuppressWarnings("unchecked")
	public T fromHash(Map<String, String> hash) {
		return hash.isEmpty() ? null : (T) this.mapper.getMapper().convertValue(hash, this.userType);
	}

	public Map<String, String> toHash(T object) {
		Map<String, String> hash = this.mapper.getMapper().convertValue(object, this.mapType);

		List<String> nullKeys = Lists.newArrayListWithCapacity(hash.size());
		for (Map.Entry<String, String> entry : hash.entrySet()) {
			if (entry.getValue() == null) {
				nullKeys.add(entry.getKey());
			}
		}

		for (String nullKey : nullKeys) {
			hash.remove(nullKey);
		}
		return hash;
	}

}
