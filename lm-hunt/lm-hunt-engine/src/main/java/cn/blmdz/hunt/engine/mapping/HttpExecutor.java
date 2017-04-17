package cn.blmdz.hunt.engine.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.common.exception.ServiceException;
import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.hunt.engine.config.model.Service;

@Component
public class HttpExecutor extends Executor<Map<String, Object>> {
	private static final Logger log = LoggerFactory.getLogger(HttpExecutor.class);

	private static final JsonMapper jsonMapper = JsonMapper.JSON_NON_EMPTY_MAPPER;
	private static final JavaType collectionType = jsonMapper.createCollectionType(HashMap.class,
			new Class[] { String.class, Object.class });

	@Override
	public boolean detectType(Service service) {
		return HttpExecutor.HttpTarget.urlPattern.matcher(service.getUri()).matches();
	}

	@Override
	public Map<String, Object> exec(Service service, Map<String, Object> params) {
		HttpExecutor.HttpTarget httpTarget = HttpExecutor.HttpTarget.parseFrom(service);
		String uri = httpTarget.getUri(params);
		Map<String, Object> cloneParams;
		if (Iterables.isEmpty(httpTarget.paramNames)) {
			cloneParams = Maps.newHashMap();

			for (String pathVar : httpTarget.paramNames) {
				cloneParams.remove(pathVar);
			}
		} else {
			cloneParams = params;
		}

		HttpRequest httpRequest;
		switch (httpTarget.method) {
		case GET:
			httpRequest = HttpRequest.get(uri, cloneParams, true);
			break;
		case POST:
			httpRequest = HttpRequest.post(uri, cloneParams, true);
			break;
		case PUT:
			httpRequest = HttpRequest.put(uri, cloneParams, true);
			break;
		case DELETE:
			httpRequest = HttpRequest.delete(uri, cloneParams, true);
			break;
		default:
			throw new IllegalStateException("Unsupported http method");
		}

		if (!httpRequest.ok()) {
			log.error("http service error: code {}, params {}, body {}",
					new Object[] { Integer.valueOf(httpRequest.code()), cloneParams, httpRequest.body() });
			throw new ServiceException("http service error");
		} else {
			String response = httpRequest.body();
			return jsonMapper.fromJson(response, collectionType);
		}
	}

	private static class HttpTarget {
		private static Map<Object, Object> methodMapping = ImmutableMap.builder().put("GET", HttpMethod.GET)
				.put("POST", HttpMethod.POST).put("DELETE", HttpMethod.DELETE).put("PUT", HttpMethod.PUT).build();
		protected static Pattern urlPattern = Pattern.compile("^\\[(\\w+)\\](https?://.+)$");
		private static Pattern pathVarPattern = Pattern.compile("\\{[^/\\}]+?\\}");
		private boolean needFormat = false;
		private HttpMethod method;
		private String originUri;
		private String formatUri;
		private List<String> paramNames;

		protected String getUri(Map<String, Object> params) {
			if (this.needFormat) {
				Object[] pathVars = new String[this.paramNames.size()];

				for (int i = 0; i < this.paramNames.size(); ++i) {
					Object p = params.get(this.paramNames.get(i));
					if (p == null) {
						HttpExecutor.log.error("variable [{}] in path [{}] should not be null", this.paramNames.get(i),
								this.originUri);
						throw new IllegalArgumentException("variable [" + (String) this.paramNames.get(i)
								+ "] in path [" + this.originUri + "] should not be null");
					}

					pathVars[i] = p;
				}

				return String.format(this.formatUri, pathVars);
			} else {
				return this.originUri;
			}
		}

		protected static HttpExecutor.HttpTarget parseFrom(Service service) {
			HttpExecutor.HttpTarget target = new HttpExecutor.HttpTarget();
			Matcher matcher = urlPattern.matcher(service.getUri());
			if (!matcher.matches()) {
				throw new IllegalArgumentException("http service url format error: " + service.getUri());
			} else {
				target.method = (HttpMethod) methodMapping.get(matcher.group(1).toUpperCase());
				target.originUri = matcher.group(2);
				if (target.originUri.contains("{")) {
					target.needFormat = true;
					target.formatUri = target.originUri.replaceAll("\\{[^/\\}]+?\\}", "%s");
					target.paramNames = Lists.newArrayList();
					Matcher m = pathVarPattern.matcher(target.originUri);

					while (m.find()) {
						target.paramNames.add(m.group());
					}
				}

				return target;
			}
		}
	}
}
