package cn.blmdz.hunt.design.service;

import java.util.Map;

public interface PagePartService {
	
	Map<String, String> findByKey(String app, String key);
	
	boolean isReleaseExists(String app, String key);
	
	Map<String, String> findReleaseByKey(String app, String key);
	
	void put(String app, String key, Map<String, String> parts);
	
	void put(String app, String key, String partKey, String part);
	
	void replace(String app, String key, Map<String, String> parts);
	
	void release(String app, String key);
	
	void delete(String app, String key);
}
