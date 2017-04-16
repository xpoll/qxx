package cn.blmdz.hunt.design.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBack;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.hunt.common.util.KeyUtils;
import cn.blmdz.hunt.design.medol.Page;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class PageDao extends RedisBaseDao<Page> {
	@Autowired
	public PageDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor);
	}

	public Page findById(Long id) {
		return findByKey(id);
	}

	public Page findByPath(Long siteId, String path) {
		Long pageId = findIdByPath(siteId, path);
		return pageId == null ? null : findByKey(pageId);
	}

	public Long findIdByPath(final Long siteId, final String path) {
		return super.jedisExecutor.execute(new JedisCallBack<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				String pageIdStr = jedis.hget(keyForSitePages(siteId), path);
				return Strings.isNullOrEmpty(pageIdStr) ? null : Long.valueOf(pageIdStr);
			}
		});
	}

	public List<Page> listBySite(final Long siteId) {
		return super.jedisExecutor.execute(new JedisCallBack<List<Page>>() {
			@Override
			public List<Page> execute(Jedis jedis) {
				Map<String, String> pageIds = jedis.hgetAll(keyForSitePages(siteId));
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
		super.jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
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
		super.jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				Transaction t = jedis.multi();
				if (!Strings.isNullOrEmpty(page.getPath()) && !Objects.equal(page.getPath(), exists.getPath())) {
					t.hdel(keyForSitePages(page.getSiteId()), new String[] { exists.getPath() });
					t.hset(keyForSitePages(page.getSiteId()), page.getPath(), page.getId().toString());
				}

				t.hmset(KeyUtils.entityId(Page.class, page.getId().longValue()), stringHashMapper.toHash(page));
				t.exec();
			}
		});
	}

	public void delete(Long pageId) {
		final Page page = findByKey(pageId);
		if (page != null) {
			super.jedisExecutor.execute(new JedisCallBackNoResult() {
				@Override
				public void execute(Jedis jedis) {
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

	protected static String keyForSitePages(Long siteId) {
		return "site:" + siteId + ":pages";
	}
}
