package cn.blmdz.hunt.design.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.hunt.design.dao.PageDao;
import cn.blmdz.hunt.design.dao.PagePartDao;
import cn.blmdz.hunt.design.dao.SiteDao;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.util.PagePartPath;
import cn.blmdz.hunt.design.util.PagePartScope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private SiteDao siteDao;
	@Autowired
	private PageDao pageDao;
	@Autowired
	private PagePartDao pagePartDao;
	@Autowired
	private JedisExecutor jedisExecutor;

	@Override
	public Page find(Long pageId) {
		Preconditions.checkNotNull(pageId, "pageId should not be null");
		return pageDao.findById(pageId);
	}

	@Override
	public Page find(Long siteId, String path) {
		Preconditions.checkNotNull(siteId, "siteId should not be null");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(path), "path should not be empty");
		return pageDao.findByPath(siteId, path);
	}

	@Override
	public Long create(Long siteId, Page page) {
		Preconditions.checkNotNull(siteId, "siteId should not be null");
		Preconditions.checkNotNull(page, "page should not be null");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(page.getPath()), "path in page should not be empty");
		Site site = siteDao.findById(siteId);
		if (site == null) {
			throw new IllegalArgumentException("site not found for id: " + siteId);
		} else {
			page.setSiteId(siteId);
			if (pageDao.findIdByPath(siteId, page.getPath()) != null) {
				throw new IllegalArgumentException("page path already exists in site");
			} else {
				return pageDao.create(page);
			}
		}
	}

	@Override
	public void update(Page page) {
		Preconditions.checkNotNull(page, "page should not be null");
		Preconditions.checkNotNull(page.getId(), "pageId should not be null");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(page.getPath()), "path in page should not be empty");
		Long existsPageId = pageDao.findIdByPath(page.getSiteId(), page.getPath());
		if (existsPageId != null && !Objects.equal(page.getId(), existsPageId)) {
			throw new IllegalArgumentException("page path already exists in site");
		} else {
			Page exists = pageDao.findById(page.getId());
			if (exists.isImmutable()) {
				exists.setTitle(page.getTitle());
				exists.setKeywords(page.getKeywords());
				exists.setDescription(page.getDescription());
				pageDao.update(exists);
			} else {
				pageDao.update(page);
			}

		}
	}

	@Override
	public List<Page> listBySite(Long siteId) {
		Preconditions.checkNotNull(siteId, "siteId should not be null");
		return pageDao.listBySite(siteId);
	}

	@Override
	public void delete(Long pageId) {
		Preconditions.checkNotNull(pageId, "pageId should not be null");
		Page page = pageDao.findById(pageId);
		delete(page);
	}

	@Override
	public void delete(Long siteId, String path) {
		Page page = pageDao.findByPath(siteId, path);
		delete(page);
	}

	@Override
	public String pagePartKeyForPage(Long pageId) {
		return (new PagePartPath(PagePartScope.PAGE.toString(), pageId, null)).toString();
	}

	private void delete(final Page page) {
		if (page != null) {
			if (!page.isImmutable()) {
				final Site site = siteDao.findById(page.getSiteId());
				jedisExecutor.execute(new JedisCallBackNoResult() {
					@Override
					public void execute(Jedis jedis) {
						Transaction t = jedis.multi();
						pageDao.delete(page, t);
						pagePartDao.delete(page.getApp(), pagePartKeyForPage(page.getId()));
						if (Objects.equal(site.getIndex(), page.getPath())) {
							siteDao.cleanSiteIndex(page.getSiteId(), t);
						}

						t.exec();
					}
				});
			}
		}
	}
}
