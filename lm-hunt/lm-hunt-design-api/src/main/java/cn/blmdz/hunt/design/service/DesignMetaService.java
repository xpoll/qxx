package cn.blmdz.hunt.design.service;

import java.util.Map;

import cn.blmdz.hunt.design.medol.DesignMetaInfo;


public interface DesignMetaService {

	DesignMetaInfo getMetaInfo();
	
	String renderPage(String domain, String path, Map<String, Object> context);
	
	String renderComponent(String appKey, String template, Map<String, Object> context);
}
