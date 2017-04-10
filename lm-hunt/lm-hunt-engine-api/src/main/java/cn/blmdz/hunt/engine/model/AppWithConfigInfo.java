package cn.blmdz.hunt.engine.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AppWithConfigInfo implements Serializable {
	private static final long serialVersionUID = 4841183019498081777L;
	
	private App app;
	private ConfigInfo frontConfig;
	private ConfigInfo backConfig;


	@Data
	@AllArgsConstructor
	public static class ConfigInfo implements Serializable {
		private static final long serialVersionUID = -4371408950758643247L;
		private String sign;
		private Date loadedAt;
	}
}