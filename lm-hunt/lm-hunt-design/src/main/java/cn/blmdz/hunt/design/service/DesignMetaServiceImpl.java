package cn.blmdz.hunt.design.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.JedisExecutor.JedisCallBackNoResult;
import cn.blmdz.hunt.design.container.DPageRender;
import cn.blmdz.hunt.design.medol.DesignMetaInfo;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.handlebars.HandlebarsEngine;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.model.AppWithConfigInfo;
import cn.blmdz.hunt.engine.service.ConfigService;
import redis.clients.jedis.Jedis;

@Service
public class DesignMetaServiceImpl implements DesignMetaService {

	@Autowired
	private JedisExecutor jedisExecutor;
	@Autowired
	private DPageRender dPageRender;
	@Autowired
	private HandlebarsEngine handlebarsEngine;
	@Autowired
	private ConfigService configService;

	@Override
	public DesignMetaInfo getMetaInfo() {
		final DesignMetaInfo metaInfo = new DesignMetaInfo();
		jedisExecutor.execute(new JedisCallBackNoResult() {
			@Override
			public void execute(Jedis jedis) {
				metaInfo.setRedisUrl(jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
			}
		});
		List<AppWithConfigInfo> apps = configService.listAllAppWithConfigInfo();
		List<String> appNames = Lists.newArrayList();

		for(AppWithConfigInfo app : apps) {
			if(app.getFrontConfig() != null) {
				appNames.add(app.getApp().getKey());
			}
		}

		metaInfo.setApps(appNames);
		return metaInfo;
	}

	@Override
	public String renderPage(String domain, String path, Map<String, Object> context) {
		return dPageRender.render(domain, path, context);
	}

	@Override
	public String renderComponent(String appKey, String template, Map<String, Object> context) {
		App app = configService.getApp(appKey);
		ThreadVars.setApp(app);
		String result = handlebarsEngine.execInline(template, context);
		ThreadVars.clearApp();
		return result;
	}

}
