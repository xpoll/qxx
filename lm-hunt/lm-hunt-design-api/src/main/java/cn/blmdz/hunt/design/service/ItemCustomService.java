package cn.blmdz.hunt.design.service;

import java.util.Map;

public interface ItemCustomService {
	
	Boolean save(Long itemId, String html);
	
	Boolean saveTemplate(Long spuId, String html);
	
	String render(Long itemId, Long spuId, Map<String, String> context);
	
	String renderTemplate(Long spuId, Map<String, String> context);
}
