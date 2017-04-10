package cn.blmdz.hunt.engine.config.model;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class BackConfig extends BaseConfig {
	private static final long serialVersionUID = 5571843585899076268L;
	@Getter
	@Setter
	private Map<String, Service> services;

	@Override
	public void merge(BaseConfig config) {
		if (config == null) {
			return;
		}
		if (!(config instanceof BackConfig)) {
			throw new IllegalArgumentException("merged config is not BackConfig");
		}
		BackConfig mergedConfig = (BackConfig) config;
		if (!CollectionUtils.isEmpty(mergedConfig.getServices())) {
			if (this.services == null) {
				this.services = Maps.newHashMap();
			}
			this.services.putAll(mergedConfig.getServices());
		}

	}
}
