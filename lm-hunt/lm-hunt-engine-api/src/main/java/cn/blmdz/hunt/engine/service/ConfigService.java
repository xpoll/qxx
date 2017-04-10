package cn.blmdz.hunt.engine.service;

import java.util.List;

import cn.blmdz.hunt.engine.config.model.BackConfig;
import cn.blmdz.hunt.engine.config.model.FrontConfig;
import cn.blmdz.hunt.engine.config.model.Render.Layout;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.model.AppWithConfigInfo;

public interface ConfigService {
	
	List<AppWithConfigInfo> listAllAppWithConfigInfo();

	App getApp(String appKey);

	FrontConfig getFrontConfig(String appKey);

	FrontConfig getDefaultFrontConfig();

	BackConfig getBackConfig(String appKey);

	BackConfig getDefaultBackConfig();

	List<Layout> listAllLayouts();

	List<Layout> listLayouts(String type);

	List<Layout> listLayouts(String app, String type);

	Layout findLayout(String app, String key);
}