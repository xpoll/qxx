package cn.blmdz.boot.hunt.autoconfigure;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "pampas.mvc")
public class PampasMVCProperties {
	/**
	 * 忽略拦截器 
	 */
	private List<Interceptors> ignoreInterceptors;
	/**
	 * 自定义拦截器
	 */
	private List<String> customInterceptors;
	/**
	 * 默认错误视图
	 */
	private String defaultErrorView;
	
	private Map<Integer, String> codeErrorViews;

	public static enum Interceptors {
		CSRFCheck, App, LocaleJudge, Cookie, Login, Auth;
	}
}
