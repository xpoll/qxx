package cn.blmdz.hunt.engine.model;

import java.io.Serializable;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class App implements Serializable {
	private static final long serialVersionUID = 6605245210822077562L;
	
	public static final String FRONT_CONFIG_FILE = "front_config.yaml";
	public static final String BACK_CONFIG_FILE = "back_config.yaml";
	public static final String CONFIG_JS_FILE = "assets/scripts/config.js";
	
	private String key;
	private String domain;
	private String judgePattern;
	private Pattern regexJudgePattern;
	private String assetsHome;
	private String configJsFile;
	private String configPath;
	private String desc;
}