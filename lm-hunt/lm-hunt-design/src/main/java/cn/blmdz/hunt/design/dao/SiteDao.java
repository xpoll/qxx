package cn.blmdz.hunt.design.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.hunt.design.medol.Site;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class SiteDao extends RedisBaseDao<Site> {
	
	@Autowired
	public SiteDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, 0);
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
		Site site = super.findById(id);
		return site == null ? null : site.getId() == null ? null : site;
	}

	public Site findByDomain(final String domain) {
		String siteId = super.jedisExecutor.execute(new JedisCallBack<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.get(keyByDomain(domain));
			}
		}, 0);
		return !Strings.isNullOrEmpty(siteId) ? this.findById(Long.valueOf(Long
				.parseLong(siteId))) : null;
	}

	public Site findBySubdomain(final String subdomain) {
		String siteId =  super.jedisExecutor.execute(new JedisCallBack<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.get(keyBySubDomain(subdomain));
			}
		}, 0);
		return !Strings.isNullOrEmpty(siteId) ? this.findById(Long.valueOf(Long
				.parseLong(siteId))) : null;
	}

	public boolean update(final Site site) {
		long siteId = site.getId().longValue();
		final Site exist = this.findById(Long.valueOf(siteId));
		if (!Objects.equal(exist.getApp(), site.getApp()))
			throw new IllegalArgumentException("can\'t change site\'s app");
		
		 super.jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				Long id = site.getId();
				t.hmset(KeyUtils.entityId(Site.class, id.longValue()), stringHashMapper.toHash(site));
				if (!Strings.isNullOrEmpty(site.getDomain())
						&& !Objects.equal(site.getDomain(), exist.getDomain())) {
					
					t.del(keyByDomain(exist.getDomain()));
					t.set(keyByDomain(site.getDomain()), id.toString());
				}

				t.exec();
			}
		}, 0);
		return true;
	}

	public void cleanSiteIndex(Long siteId, Transaction t) {
		t.hdel(KeyUtils.entityId(Site.class, siteId.longValue()), new String[] { "index" });
	}

	public List<Site> findByUserId(String app, final long userId) {
		Set<String> ids =  super.jedisExecutor.execute(new JedisCallBack<Set<String>>() {
			@Override
			public Set<String> execute(Jedis jedis) {
				return jedis.smembers(keyUserSites(Long.valueOf(userId)));
			}
		}, 0);
		return findByKeys(ids);
	}

	public List<Site> listAll() {
		Set<String> ids =  super.jedisExecutor.execute(new JedisCallBack<Set<String>>() {
			@Override
			public Set<String> execute(Jedis jedis) {
				return jedis.smembers(keySites());
			}
		}, 0);
		return findByKeys(ids);
	}

	protected static String keySites() {
		return "sites";
	}

	protected static String keyUserSites(Long userId) {
		return "user:" + userId + ":sites";
	}

	protected static String keyByDomain(String domain) {
		return "site:domain:" + domain;
	}

	protected static String keyBySubDomain(String subDomain) {
		return "site:sub-domain:" + subDomain;
	}
}
