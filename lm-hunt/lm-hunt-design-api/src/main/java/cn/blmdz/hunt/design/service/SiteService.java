package cn.blmdz.hunt.design.service;


import java.util.List;

import cn.blmdz.hunt.design.medol.Site;

public interface SiteService {
	
	Long create(Site site, boolean userOnly);
	
	void delete(Long siteId);
	
	Boolean update(Site site);
	
	void release(Long siteId);
	
	List<Site> listAll();
	
	Site findByDomain(String domain);
	
	List<Site> findByUserId(String app, Long userId);
	
	Site findById(Long siteId);
	
	Site findByPageId(Long pageId);
	
	void clearIndex(Long siteId);
}
