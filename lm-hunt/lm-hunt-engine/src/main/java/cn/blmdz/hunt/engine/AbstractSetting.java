package cn.blmdz.hunt.engine;

import java.util.List;

import cn.blmdz.hunt.engine.model.App;
import lombok.Data;

@Data
public abstract class AbstractSetting {
	/**
	 * 前端项目路径
	 */
	private String rootPath;
	/**
	 * app
	 */
	private List<App> apps;
	private String registryId;
	private boolean devMode = false;
	/**
	 * 语言
	 */
	private String locale;
	private boolean clearInjectNestedContext = false;
}