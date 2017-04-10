package cn.blmdz.hunt.engine;

import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.engine.model.App;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Setting extends AbstractSetting {
	private Map<String, App> appMap = Maps.newHashMap();
	private App defaultApp;

	@PostConstruct
	private void init() {
		Preconditions.checkNotNull(this.getRootPath(), "rootPath in [Setting] should not be null");
		Preconditions.checkNotNull(this.getApps(), "fronts in [Setting] should not be null");
		this.setRootPath(this.normalize(this.getRootPath()));

		for (App app : this.getApps()) {
			Preconditions.checkNotNull(app.getKey(), "key in App should not be null");
			app.setAssetsHome(this.normalize(this.getRootPath() + Strings.nullToEmpty(app.getAssetsHome())));
			if (Strings.isNullOrEmpty(app.getConfigPath())) {
				app.setConfigPath(app.getAssetsHome() + "back_config.yaml");
			}

			app.setConfigJsFile(MoreObjects.firstNonNull(app.getConfigJsFile(), "assets/scripts/config.js"));
			if (!Strings.isNullOrEmpty(app.getJudgePattern())) {
				app.setRegexJudgePattern(Pattern.compile(app.getJudgePattern()));
			}

			this.appMap.put(app.getKey(), app);
		}

		this.defaultApp = this.getApps().get(0);
	}

	private String normalize(String rootPath) {
		return rootPath.endsWith("/") ? rootPath : rootPath + "/";
	}
}
