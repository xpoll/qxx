package cn.blmdz.hunt.design.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.design.dao.ItemCustomRedisDao;
import cn.blmdz.hunt.design.dao.ItemTemplateRedisDao;
import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;

@Service
public class ItemCustomServiceImpl implements ItemCustomService {
	private static final Logger log = LoggerFactory.getLogger(ItemCustomServiceImpl.class);
	@Autowired
	private ItemCustomRedisDao itemCustomRedisDao;
	@Autowired
	private ItemTemplateRedisDao itemTemplateRedisDao;
	@Autowired
	private HandlebarsEngine handlebarsEngine;
	private final LoadingCache<String, Optional<String>> cache;
	private static final Splitter keySplitter = Splitter.on('-').limit(2)
			.omitEmptyStrings().trimResults();

	@Autowired
	public ItemCustomServiceImpl(Setting setting) {
		cache = optionalCache(!setting.isDevMode());
	}

	private LoadingCache<String, Optional<String>> optionalCache(boolean buildCache) {
		return buildCache ? CacheBuilder.newBuilder().maximumSize(100000L)
				.expireAfterWrite(5L, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Optional<String>>() {
					@Override
					public Optional<String> load(String key) throws Exception {
						List<String> parts = keySplitter.splitToList(key);
						String type = (String) parts.get(0);
						Long id = Long.valueOf(Long.parseLong(parts.get(1)));
						String hbs;
						if (Objects.equal(type, "custom")) {
							hbs = itemCustomRedisDao.findById(id.longValue());
						} else {
							hbs = itemTemplateRedisDao.findBySpuId(id.longValue());
						}

						return Optional.fromNullable(hbs);
					}
				})
				: null;
	}

	@Override
	public Boolean save(Long itemId, String html) {
		itemCustomRedisDao.createOrUpdate(itemId.longValue(), html);
		return Boolean.valueOf(true);
	}

	@Override
	public Boolean saveTemplate(Long spuId, String html) {
		itemTemplateRedisDao.createOrUpdate(spuId.longValue(), html);
		return Boolean.valueOf(true);
	}

	@Override
	public String render(Long itemId, Long spuId, Map<String, String> context) {
		boolean designMode = context.containsKey(RenderConstants.DESIGN_MODE);
		String hbs;
		if (cache != null && !designMode) {
			hbs = cache.getUnchecked("custom-" + itemId).orNull();
		} else {
			hbs = itemCustomRedisDao.findById(itemId.longValue());
		}

		if (hbs != null) {
			Map<String, Object> renderContext = Maps.newHashMap();
			renderContext.put("spuId", spuId);
			if (designMode) {
				renderContext.put(RenderConstants.DESIGN_MODE, Boolean.valueOf(true));
			}

			return handlebarsEngine.execInline(hbs, renderContext);
		} else {
			log.error("item content not found. itemId [{}], spuId [{}]", itemId, spuId);
			return "";
		}
	}

	@Override
	public String renderTemplate(Long spuId, Map<String, String> context) {
		boolean designMode = context.containsKey(RenderConstants.DESIGN_MODE);
		String hbs;
		if (cache != null && !designMode) {
			hbs = cache.getUnchecked("template-" + spuId).orNull();
		} else {
			hbs = itemTemplateRedisDao.findBySpuId(spuId.longValue());
		}

		if (hbs != null) {
			Map<String, Object> renderContext = Maps.newHashMap();
			if (designMode) {
				renderContext.put(RenderConstants.DESIGN_MODE, Boolean.valueOf(true));
			}

			return handlebarsEngine.execInline(hbs, renderContext);
		} else {
			log.debug("item template content not found. spuId [{}]", spuId);
			return "";
		}
	}
}
