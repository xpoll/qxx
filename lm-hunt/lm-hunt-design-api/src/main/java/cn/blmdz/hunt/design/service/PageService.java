package cn.blmdz.hunt.design.service;


import java.util.List;

import cn.blmdz.hunt.design.medol.Page;

public interface PageService {
	
	Page find(Long pageId);
	
	Page find(Long siteId, String path);
	
	Long create(Long siteId, Page page);
	
	void update(Page page);
	
	List<Page> listBySite(Long siteId);
	
	void delete(Long pageId);
	
	void delete(Long siteId, String path);
	
	String pagePartKeyForPage(Long pageId);
}
