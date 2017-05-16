package cn.blmdz.hbs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix="qxx")
public class HbsProperties {
	private String root;
	private String home;
	private String locale;
	private Boolean devMode = Boolean.TRUE.booleanValue();
	
	private OOS oos;
	
	@Data
	public static class OOS {
		private String endpoint;
		private String accessKey;
		private String accessSecret;
		private String bucketName;
	}
}
