package cn.blmdz.hunt.engine;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.base.Strings;

import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.utils.Domains;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
public class SettingHelper {
	@Autowired
	protected Setting setting;

	public SettingHelper.AppResult findApp(HttpServletRequest request) {
		if (this.setting.getApps().size() == 1) {
			return SettingHelper.AppResult.sure(this.setting.getDefaultApp());
		} else {
			String domain = Domains.getDomainFromRequest(request);

			for (App app : this.setting.getApps()) {
				if (app.getRegexJudgePattern() != null && app.getRegexJudgePattern().matcher(domain).matches()) {
					return SettingHelper.AppResult.fuzzy(app);
				}
			}

			return SettingHelper.AppResult.fuzzy(this.setting.getDefaultApp());
		}
	}

	public App getDefaultApp() {
		return this.setting.getDefaultApp();
	}

	public String getDefaultAppKey() {
		return this.getDefaultApp().getKey();
	}

	public Locale getLocale() {
		return Strings.isNullOrEmpty(this.setting.getLocale()) ? null
				: StringUtils.parseLocaleString(this.setting.getLocale());
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class AppResult {
		private App app;
		private boolean fuzzy;

		public static SettingHelper.AppResult fuzzy(App app) {
			return new SettingHelper.AppResult(app, true);
		}

		public static SettingHelper.AppResult sure(App app) {
			return new SettingHelper.AppResult(app, false);
		}
	}
}