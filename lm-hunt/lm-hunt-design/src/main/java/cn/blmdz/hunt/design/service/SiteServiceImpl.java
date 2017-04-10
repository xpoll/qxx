package cn.blmdz.hunt.design.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import cn.blmdz.hunt.design.dao.PageDao;
import cn.blmdz.hunt.design.dao.SiteDao;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.util.PagePartPath;
import cn.blmdz.hunt.design.util.PagePartScope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Service
public class SiteServiceImpl implements SiteService {
	@Autowired
	private SiteDao siteDao;
	@Autowired
	private PageDao pageDao;
	@Autowired
	@Qualifier("pampasJedisTemplate")
	private JedisTemplate template;
	@Autowired
	private PagePartService pagePartService;

	@Override
	public Long create(final Site site, final boolean userOnly) {
		Preconditions.checkNotNull(site.getUserId(), "userId 必须传入");
		Preconditions.checkNotNull(site.getApp(), "app 必须传入");
		Preconditions.checkNotNull(site.getLayout(), "layout 必须传入");
		Preconditions.checkNotNull(site.getDomain(), "domain 必须传入");
		checkDomain(site);
		Long siteId = siteDao.newId();
		site.setId(siteId);
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				siteDao.create(t, site, userOnly);
				t.exec();
			}
		});
		return siteId;
	}

	@Override
	public void delete(Long siteId) {
		final Site site = siteDao.findById(siteId);
		if (site != null) {
			final List<Page> pages = pageDao.listBySite(siteId);
			template.execute(new JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Transaction t = jedis.multi();
					siteDao.delete(t, site);

					for (Page page : pages) {
						pageDao.delete(page, t);
					}

					t.exec();
				}
			});
		}
	}

	@Override
	public Boolean update(Site site) {
		checkDomain(site);
		return Boolean.valueOf(siteDao.update(site));
	}

	@Override
	public void release(Long siteId) {
		for (Page page : pageDao.listBySite(siteId)) {
			PagePartPath pagePartPath = new PagePartPath(PagePartScope.PAGE.toString(), page.getId(), null);
			pagePartService.release(page.getApp(), pagePartPath.toString());
		}
		Site site = siteDao.findById(siteId);
		pagePartService.release(site.getApp(), (new PagePartPath(
				PagePartScope.SITE.toString(), siteId, null))
				.toString());
	}

	@Override
	public List<Site> listAll() {
		return siteDao.listAll();
	}

	@Override
	public Site findByDomain(String domain) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(domain), "domain can not be empty");
		return siteDao.findByDomain(domain);
	}

	@Override
	public List<Site> findByUserId(String app, Long userId) {
		return siteDao.findByUserId(app, userId.longValue());
	}

	@Override
	public Site findById(Long siteId) {
		return siteDao.findById(siteId);
	}

	@Override
	public Site findByPageId(Long pageId) {
		Page page = pageDao.findById(pageId);
		return page == null ? null : findById(page.getSiteId());
	}

	@Override
	public void clearIndex(final Long siteId) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				siteDao.cleanSiteIndex(siteId, t);
				t.exec();
			}
		});
	}

	private void checkDomain(Site site) {
		if (!Strings.isNullOrEmpty(site.getDomain())) {
			Site domainSite = siteDao.findByDomain(site.getDomain());
			if (domainSite != null && !Objects.equal(domainSite.getId(), site.getId()))
				throw new IllegalArgumentException("domain exists");
		}

	}
}
