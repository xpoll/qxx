package cn.blmdz.hunt.engine.mapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.common.util.Splitters;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.config.ConfigManager;
import cn.blmdz.hunt.engine.config.model.HttpMethod;
import cn.blmdz.hunt.engine.config.model.Mapping;
import cn.blmdz.hunt.engine.config.model.Service;
import cn.blmdz.hunt.engine.config.model.Service.ServiceType;

@Component
public class Invoker {
	private static final Logger log = LoggerFactory.getLogger(Invoker.class);
	@Autowired
	private ConfigManager configManager;
	@Autowired
	private SpringExecutor springExecutor;
	@Autowired
	private HttpExecutor httpExecutor;
	@Autowired(required = false)
	private DubboExecutor dubboExecutor;
	
	private Map<ServiceType, Executor<?>> executorMap = Maps.newLinkedHashMap();

	@PostConstruct
	public void init() {
		executorMap.put(ServiceType.HTTP, httpExecutor);
		executorMap.put(ServiceType.SPRING, springExecutor);
		executorMap.put(ServiceType.DUBBO, dubboExecutor);
	}

	public Object invoke(String serviceUri, Map<String, Object> params) {
		List<String> serviceInfo = Splitters.COLON.splitToList(serviceUri);
		return serviceInfo.size() == 1 ? invoke(ThreadVars.getAppKey(), serviceInfo.get(0), params)
				: invoke(serviceInfo.get(0), serviceInfo.get(1), params);
	}

	public Object invoke(String app, String key, Map<String, Object> params) {
		Preconditions.checkNotNull(key, "Service key should not be null");
		Service service = configManager.findService(app, key);
		if (service == null) {
			log.error("Service not found: app {}, key {}", app, key);
			throw new NullPointerException("Service not found: " + app + ":" + key);
		} else {
			Executor<?> executor = detectExecutor(service);
			return executor.exec(service, params);
		}
	}

	public Mapping mappingMatch(String app, String urlWithMethod) {
		Preconditions.checkNotNull(urlWithMethod, "url should not be null");
		urlWithMethod = normalize(urlWithMethod);
		Set<Mapping> mappings = configManager.findMappings(app);
		if (mappings != null && !mappings.isEmpty()) {
			List<String> urls = Splitters.COLON.splitToList(urlWithMethod);
			HttpMethod method = HttpMethod.valueOf(urls.get(0));
			String url = urls.get(1);
			Mapping targetMapping = null;
			int targetMatchCount = Integer.MAX_VALUE;

			for (Mapping mapping : mappings) {
				int matchCount = match(method, url, mapping);
				if (matchCount != -1) {
					if (matchCount == 0) {
						targetMapping = mapping;
						break;
					}

					if (matchCount < targetMatchCount) {
						targetMatchCount = matchCount;
						targetMapping = mapping;
					}
				}
			}

			if (log.isDebugEnabled() && targetMapping == null) {
				log.debug("Can not match any mapping, url: [{}]", urlWithMethod);
			}

			return targetMapping;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Mappings config is empty, app: [{}]", app);
			}

			return null;
		}
	}

	public Object mappingInvoke(Mapping mapping, String url, Map<String, Object> params) {
		Preconditions.checkNotNull(url, "url should not be null");
		Preconditions.checkNotNull(mapping, "mapping should not be null");
		params.putAll(parse(url, mapping.getPattern()));
		return invoke(mapping.getService(), params);
	}

	private Executor<?> detectExecutor(Service service) {
		if (service.getType() == ServiceType.NOT_SET) {
			for (ServiceType type : executorMap.keySet()) {
				Executor<?> executor = executorMap.get(type);
				if (executor.detectType(service)) {
					service.setAutoDetectedType(type);
					break;
				}
			}

			if (service.getType() == ServiceType.NOT_SET) {
				service.setAutoDetectedType(ServiceType.CANT_DETECTED);
			}
		}

		if (service.getType() == ServiceType.CANT_DETECTED) {
			log.error("Service type can not be detected: {}", service);
			throw new IllegalStateException("Service type can not be detected, can not find a executor for it.");
		} else {
			return executorMap.get(service.getType());
		}
	}

	private int match(HttpMethod method, String url, Mapping mapping) {
		if (!CollectionUtils.isEmpty(mapping.getMethods()) && !mapping.getMethods().contains(method)) {
			return -1;
		} else {
			String pattern = mapping.getPattern();
			String[] mappingSplits = url.split("/");
			String[] patternSplits = pattern.split("/");
			if (mappingSplits.length != patternSplits.length) {
				return -1;
			} else {
				int matchCount = 0;

				for (int i = 0; i < mappingSplits.length; ++i) {
					if (patternSplits[i].startsWith("{")) {
						++matchCount;
					} else if (!patternSplits[i].equals(mappingSplits[i])) {
						return -1;
					}
				}

				return matchCount;
			}
		}
	}

	private Map<String, String> parse(String url, String pattern) {
		Map<String, String> patterns = Maps.newHashMap();
		if (!pattern.contains("{")) {
			return patterns;
		} else {
			List<String> mappingSplits = Splitters.SLASH.splitToList(url);
			List<String> patternSplits = Splitters.SLASH.splitToList(pattern);
			if (mappingSplits.size() != patternSplits.size()) {
				throw new IllegalArgumentException("not matched url");
			} else {
				for (int i = 0; i < mappingSplits.size(); ++i) {
					String patternPart = (String) patternSplits.get(i);
					String mappingPart = (String) mappingSplits.get(i);
					if (patternPart.startsWith("{")) {
						patterns.put(patternPart.substring(1, patternPart.length() - 1), mappingPart);
					} else if (!patternPart.equals(mappingPart)) {
						throw new IllegalArgumentException("not matched url");
					}
				}

				return patterns;
			}
		}
	}

	private String normalize(String url) {
		return url.startsWith("/") ? url.substring(1) : url;
	}
}
