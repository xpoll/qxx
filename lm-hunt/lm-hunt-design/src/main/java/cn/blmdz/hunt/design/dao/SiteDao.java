package cn.blmdz.hunt.design.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.dao.RedisBaseDao;
import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import cn.blmdz.hunt.common.util.KeyUtils;
import cn.blmdz.hunt.design.medol.Site;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class SiteDao extends RedisBaseDao<Site> {
	@Autowired
	public SiteDao(@Qualifier("pampasJedisTemplate") JedisTemplate template) {
		super(template);
	}

	public void create(Transaction t, Site site, boolean userOnly) {
		Long id = site.getId();
		t.hmset(KeyUtils.entityId(Site.class, id.longValue()), stringHashMapper.toHash(site));
		if (!userOnly) {
			t.sadd(keySites(), new String[] { id.toString() });
		}

		t.sadd(keyUserSites(site.getUserId()), new String[] { id.toString() });
		if (!Strings.isNullOrEmpty(site.getDomain())) {
			t.set(keyByDomain(site.getDomain()), id.toString());
		}

	}

	public void delete(Transaction t, Site site) {
		t.del(KeyUtils.entityId(Site.class, site.getId().longValue()));
		t.srem(keySites(), new String[] { site.getId().toString() });
		t.srem(keyUserSites(site.getUserId()), new String[] { site.getId()
				.toString() });
		if (!Strings.isNullOrEmpty(site.getDomain())) {
			t.del(keyByDomain(site.getDomain()));
		}

	}

	public Site findById(Long id) {
		Site site = findByKey(id);
		return site.getId() != null ? site : null;
	}

	public Site findByDomain(final String domain) {
		String siteId = template.execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(SiteDao.keyByDomain(domain));
			}
		});
		return !Strings.isNullOrEmpty(siteId) ? this.findById(Long.valueOf(Long
				.parseLong(siteId))) : null;
	}

	public Site findBySubdomain(final String subdomain) {
		String siteId = template.execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(SiteDao.keyBySubDomain(subdomain));
			}
		});
		return !Strings.isNullOrEmpty(siteId) ? this.findById(Long.valueOf(Long
				.parseLong(siteId))) : null;
	}

	public boolean update(final Site site) {
		long siteId = site.getId().longValue();
		final Site exist = this.findById(Long.valueOf(siteId));
		if (!Objects.equal(exist.getApp(), site.getApp()))
			throw new IllegalArgumentException("can\'t change site\'s app");
		
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				Long id = site.getId();
				t.hmset(KeyUtils.entityId(Site.class, id.longValue()), stringHashMapper.toHash(site));
				if (!Strings.isNullOrEmpty(site.getDomain())
						&& !Objects.equal(site.getDomain(), exist.getDomain())) {
					
					t.del(SiteDao.keyByDomain(exist.getDomain()));
					t.set(SiteDao.keyByDomain(site.getDomain()), id.toString());
				}

				t.exec();
			}
		});
		return true;
	}

	public void cleanSiteIndex(Long siteId, Transaction t) {
		t.hdel(KeyUtils.entityId(Site.class, siteId.longValue()),
				new String[] { "index" });
	}

	public List<Site> findByUserId(String app, final long userId) {
		Set<String> ids = template.execute(new JedisAction<Set<String>>() {
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.smembers(SiteDao.keyUserSites(Long.valueOf(userId)));
			}
		});
		return findByIds(ids);
	}

	public List<Site> listAll() {
		Set<String> ids = template.execute(new JedisAction<Set<String>>() {
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.smembers(SiteDao.keySites());
			}
		});
		return findByIds(ids);
	}

	public static String keySites() {
		return "sites";
	}

	public static String keyUserSites(Long userId) {
		return "user:" + userId + ":sites";
	}

	public static String keyByDomain(String domain) {
		return "site:domain:" + domain;
	}

	public static String keyBySubDomain(String subDomain) {
		return "site:sub-domain:" + subDomain;
	}
}
