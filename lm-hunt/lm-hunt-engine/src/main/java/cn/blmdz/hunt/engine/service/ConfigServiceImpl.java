package cn.blmdz.hunt.engine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.config.ConfigManager;
import cn.blmdz.hunt.engine.config.model.BackConfig;
import cn.blmdz.hunt.engine.config.model.FrontConfig;
import cn.blmdz.hunt.engine.config.model.Render;
import cn.blmdz.hunt.engine.config.model.Render.Layout;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.model.AppWithConfigInfo;
import cn.blmdz.hunt.engine.model.AppWithConfigInfo.ConfigInfo;

@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigManager configManager;
	@Autowired
	private Setting setting;

	@Override
	public List<AppWithConfigInfo> listAllAppWithConfigInfo() {
		List<App> appList = this.configManager.listAllApp();
		List<AppWithConfigInfo> result = Lists.newArrayList();

		for (App app : appList) {
			AppWithConfigInfo appWithConfigInfo = new AppWithConfigInfo();
			appWithConfigInfo.setApp(app);
			FrontConfig frontConfig = this.configManager.getFrontConfig(app.getKey());
			if (frontConfig != null) {
				appWithConfigInfo.setFrontConfig(new ConfigInfo(frontConfig.getSign(), frontConfig.getLoadedAt()));
			}

			BackConfig backConfig = this.configManager.getBackConfig(app.getKey());
			if (backConfig != null) {
				appWithConfigInfo.setBackConfig(new ConfigInfo(backConfig.getSign(), backConfig.getLoadedAt()));
			}

			result.add(appWithConfigInfo);
		}

		return result;
	}

	@Override
	public App getApp(String appKey) {
		for (App app : this.configManager.listAllApp()) {
			if (Objects.equal(app.getKey(), appKey)) {
				return app;
			}
		}

		return null;
	}

	@Override
	public FrontConfig getFrontConfig(String appKey) {
		return this.configManager.getFrontConfig(appKey);
	}

	@Override
	public FrontConfig getDefaultFrontConfig() {
		return this.configManager.getFrontConfig(this.setting.getDefaultApp().getKey());
	}

	@Override
	public BackConfig getBackConfig(String appKey) {
		return this.configManager.getBackConfig(appKey);
	}

	@Override
	public BackConfig getDefaultBackConfig() {
		return this.configManager.getBackConfig(this.setting.getDefaultApp().getKey());
	}

	@Override
	public List<Layout> listAllLayouts() {
		return this.listLayouts((String) null);
	}

	@Override
	public List<Layout> listLayouts(String type) {
		List<Layout> result = Lists.newArrayList();

		for (App app : this.setting.getApps()) {
			result.addAll(this.listLayouts(app.getKey(), type));
		}

		return result;
	}

	@Override
	public List<Layout> listLayouts(String app, String type) {
		List<Layout> result = Lists.newArrayList();
		Render render = this.getFrontConfig(app).getRender();
		if (render != null && render.getLayouts() != null) {
			if (Strings.isNullOrEmpty(type)) {
				result.addAll(render.getLayouts().values());
			} else {
				for (Layout layout : render.getLayouts().values()) {
					if (Objects.equal(type, layout.getType())) {
						result.add(layout);
					}
				}
			}
		}

		return result;
	}

	@Override
	public Layout findLayout(String app, String key) {
		Render render = this.getFrontConfig(app).getRender();
		return render != null && render.getLayouts() != null ? (Layout) render.getLayouts().get(key) : null;
	}
}