package cn.blmdz.hunt.engine.config.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class FrontConfig extends BaseConfig {
	private static final long serialVersionUID = 7453802881470194536L;
	public static final String HREF_BASE = "base";
	public static final String HREF_MAIN = "main";
	public static final String HREF_LOGIN = "login";

	private Map<String, Component> components;
	private Map<String, List<Component>> componentCategoryListMap = Maps.newHashMap();
	private Set<Mapping> mappings;
	private Auths auths;
	private Render render;
	private Map<String, String> hrefs = Maps.newHashMap();
	private Map<String, String> vars = Maps.newHashMap();

	public void merge(BaseConfig config) {
		if (config == null) {
			return;
		}
		if (!(config instanceof FrontConfig)) {
			throw new IllegalArgumentException("merged config is not FrontConfig");
		}
		FrontConfig mergedConfig = (FrontConfig) config;
		
		if (!CollectionUtils.isEmpty(mergedConfig.getComponents())) {
			if (this.components == null) {
				this.components = Maps.newHashMap();
			}
			this.components.putAll(mergedConfig.getComponents());
		}
		
		if (!CollectionUtils.isEmpty(mergedConfig.getMappings())) {
			if (this.mappings == null) {
				this.mappings = Sets.newHashSet();
			}
			this.mappings.addAll(mergedConfig.getMappings());
		}

		if (mergedConfig.getAuths() != null) {
			if (this.auths == null) {
				this.auths = new Auths();
			}
			this.auths.merge(mergedConfig.getAuths());
		}

		if (mergedConfig.getRender() != null) {
			if (this.render == null) {
				this.render = new Render();
			}
			this.render.merge(mergedConfig.getRender());
		}
		
		if (!CollectionUtils.isEmpty(mergedConfig.getHrefs())) {
			if (this.hrefs == null) {
				this.hrefs = Maps.newHashMap();
			}
			this.hrefs.putAll(mergedConfig.getHrefs());
		}
		
		if (!CollectionUtils.isEmpty(mergedConfig.getVars())) {
			if (this.vars == null) {
				this.vars = Maps.newHashMap();
			}
			this.vars.putAll(mergedConfig.getVars());
		}
	}

	public Map<String, String> getCurrentHrefs(String currentDomain) {
		Map<String, String> currentHrefs = Maps.newHashMap(this.hrefs);
		if (currentHrefs.get("base") == null) {
			currentHrefs.put("base", currentDomain);
		}
		if (currentHrefs.get("main") == null) {
			currentHrefs.put("main", "http://" + currentDomain);
		}
		if (currentHrefs.get("login") == null) {
			currentHrefs.put("login", currentHrefs.get("main") + "/login");
		}
		return currentHrefs;
	}

	public String getVar(String key) {
		return (this.vars == null) ? null : this.vars.get(key);
	}
}
