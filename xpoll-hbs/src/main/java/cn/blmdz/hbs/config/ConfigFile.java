package cn.blmdz.hbs.config;

import java.util.Map;

import lombok.Data;

@Data
public class ConfigFile {
	private Map<String, Components> components;
	private Object defaults;
	private Object trees;
	private Object resources;
}
