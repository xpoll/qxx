package cn.blmdz.boot.hunt.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import cn.blmdz.hunt.engine.AbstractSetting;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "pampas")
public class PampasProperties extends AbstractSetting {
	/**
	 * redis配置
	 */
	private RedisProperties redis;

	@Data
	public static class RedisProperties {
		private String jedisPool;
		private String host;
		private Integer port;
		private Integer timeout;
		private String password;
		private Integer database;
	}
}
