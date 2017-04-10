package cn.blmdz.hunt.design.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.blmdz.hunt.common.dao.RedisBaseDao;
import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisAction;
import cn.blmdz.hunt.common.util.JedisTemplate.JedisActionNoResult;
import cn.blmdz.hunt.common.util.KeyUtils;
import cn.blmdz.hunt.design.medol.Page;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class PageDao extends RedisBaseDao<Page> {
	@Autowired
	public PageDao(@Qualifier("pampasJedisTemplate") JedisTemplate template) {
		super(template);
	}

	public Page findById(Long id) {
		return findByKey(id);
	}

	public Page findByPath(Long siteId, String path) {
		Long pageId = findIdByPath(siteId, path);
		return pageId == null ? null : findByKey(pageId);
	}

	public Long findIdByPath(final Long siteId, final String path) {
		return template.execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				String pageIdStr = jedis.hget(PageDao.keyForSitePages(siteId),
						path);
				return Strings.isNullOrEmpty(pageIdStr) ? null : Long
						.valueOf(pageIdStr);
			}
		});
	}

	public List<Page> listBySite(final Long siteId) {
		return template.execute(new JedisAction<List<Page>>() {
			@Override
			public List<Page> action(Jedis jedis) {
				Map<String, String> pageIds = jedis.hgetAll(PageDao
						.keyForSitePages(siteId));
				List<Page> result = Lists.newArrayList();

				for (String pageIdStr : pageIds.values()) {
					if (!Strings.isNullOrEmpty(pageIdStr)) {
						result.add(findByKey(pageIdStr));
					}
				}

				return result;
			}
		});
	}

	public Long create(final Page page) {
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				create(page, t);
				t.exec();
			}
		});
		return page.getId();
	}

	public Long create(Page page, Transaction t) {
		page.setId(newId());
		t.hset(keyForSitePages(page.getSiteId()), page.getPath(), page.getId().toString());
		t.hmset(KeyUtils.entityId(Page.class, page.getId().longValue()), stringHashMapper.toHash(page));
		return page.getId();
	}

	public void update(final Page page) {
		final Page exists = findById(page.getId());
		page.setSiteId(exists.getSiteId());
		page.setApp(exists.getApp());
		template.execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				Transaction t = jedis.multi();
				if (!Strings.isNullOrEmpty(page.getPath()) && !Objects.equal(page.getPath(), exists.getPath())) {
					t.hdel(PageDao.keyForSitePages(page.getSiteId()), new String[] { exists.getPath() });
					t.hset(PageDao.keyForSitePages(page.getSiteId()), page.getPath(), page.getId().toString());
				}

				t.hmset(KeyUtils.entityId(Page.class, page.getId().longValue()), stringHashMapper.toHash(page));
				t.exec();
			}
		});
	}

	public void delete(Long pageId) {
		final Page page = findByKey(pageId);
		if (page != null) {
			template.execute(new JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Transaction t = jedis.multi();
					delete(page, t);
					t.exec();
				}
			});
		}

	}

	public void delete(Page page, Transaction t) {
		t.hdel(keyForSitePages(page.getSiteId()), new String[] { page.getPath() });
		t.del(KeyUtils.entityId(Page.class, page.getId().longValue()));
	}

	public static String keyForSitePages(Long siteId) {
		return "site:" + siteId + ":pages";
	}
}
