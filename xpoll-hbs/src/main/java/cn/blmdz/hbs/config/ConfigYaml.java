package cn.blmdz.hbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import cn.blmdz.hbs.file.FileLoaderHelper;

@Component
public class ConfigYaml {

	@Autowired
	private FileLoaderHelper fileLoaderHelper;
	
	private ConfigFile load() {
		return new Yaml().loadAs(fileLoaderHelper.load("classpath:config_file.yaml").asString(), ConfigFile.class);
	}
	
	public Components loadComponent(String path) {
		return this.load().getComponents().get(path);
	}

}
