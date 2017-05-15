package cn.blmdz.hbs.config;

import java.util.Map;

import lombok.Data;

@Data
public class ConfigFile {
	private Map<String, Components> components;

}
