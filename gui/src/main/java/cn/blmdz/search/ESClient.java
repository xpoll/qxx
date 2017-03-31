package cn.blmdz.search;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.util.JsonUtil;

public class ESClient {
	private final ObjectMapper objectMapper;
	private final String hostname;
	private final int port;

	public ESClient(String hostname, int port) {
		this.objectMapper = JsonUtil.JSON_NON_EMPTY_MAPPER;
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * 心跳
	 */
	public boolean health() {
		try {
			String url = "http://" + this.hostname + ":" + this.port + "/_cluster/health";

			HttpRequest request = HttpRequest.get(url);
			if (isOk(request.code())) return true;
			
			return false;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * index不存在则创建index
	 */
	public void createIndexIfNotExists(String indexName) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName;

		if (isOk(HttpRequest.head(url).code())) return;
		
		HttpRequest request = HttpRequest.put(url);
		System.out.println(request.code() + "-->" + request.body());
	}

	/**
	 * 删除index
	 */
	public void deleteIndex(String indexName) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName;

		HttpRequest request = HttpRequest.delete(url);
		System.out.println(request.code() + "-->" + request.body());
	}

	/**
	 * mapping不存在则创建mapping
	 */
	public void createMappingIfNotExists(String indexName, String indexType, String mapping) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName + "/" + indexType;

		if (isOk(HttpRequest.head(url).code())) return;

		HttpRequest request = HttpRequest.put(url + "/_mapping").send(mapping);
		System.out.println(request.code() + "-->" + request.body());
	}

	/**
	 * 加入
	 */
	public void index(String indexName, String indexType, Object id, String document) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName + "/" + indexType + "/" + id;

		HttpRequest request = HttpRequest.post(url).send(document);
		System.out.println(request.code() + "-->" + request.body());
	}

	private boolean isOk(int code) {
		if ((code >= 200) && (code < 300))
			System.out.println("ok");
		return (code >= 200) && (code < 300);
	}

	/**
	 * 删除
	 */
	public void delete(String indexName, String indexType, Object id) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName + "/" + indexType + "/" + id;
		HttpRequest request = HttpRequest.delete(url);
		System.out.println(request.code() + "-->" + request.body());
	}

//	public void deleteByQuery(String indexName, String indexType, String criteria) {
//	}

	/**
	 * 加入 bulk
	 */
	public void bulk(String indexName, String indexType, String payload) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName + "/" + indexType + "/_bulk";
		HttpRequest request = HttpRequest.post(url).send(payload);
		System.out.println(request.code() + "-->" + request.body());
	}

	/**
	 * 搜索
	 */
	public Response search(String indexName, String indexType, String criteria) {
		String url = "http://" + this.hostname + ":" + this.port + "/" + indexName + "/" + indexType + "/_search";
		HttpRequest request = HttpRequest.post(url).send(criteria);
		String body = request.body();
		System.out.println(request.code() + "-->" + request.body());
		try {
			return (Response) this.objectMapper.readValue(body, Response.class);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addWord(String httpHost, Integer httpPort, String word) {
		String url = "http://" + httpHost + ":" + httpPort + "/_dict";

		HttpRequest request = HttpRequest.get(HttpRequest.encode(url + "?word=" + word));
		System.out.println(request.code() + "-->" + request.body());
		return true;
	}

	@SuppressWarnings("deprecation")
	public List<String> getNodesIp() {
		String url = "http://" + this.hostname + ":" + this.port + "/_cat/nodes?h=ip";

		HttpRequest request = HttpRequest.get(url);
		System.out.println(request.code() + "-->" + request.body());
		return Splitter.on(CharMatcher.BREAKING_WHITESPACE).omitEmptyStrings().trimResults()
				.splitToList(request.body());
	}

	public boolean addWord(String word) {
		List<String> nodes = getNodesIp();
		for (String node : nodes) {
			addWord(node, Integer.valueOf(this.port), word);
		}
		return true;
	}

	public List<String> cutWords(String sentence, String mode) throws Exception {
		String url = "http://" + this.hostname + ":" + this.port + "/_analyze";

		Map<String, String> params = Maps.newHashMap();
		params.put("analyzer", mode);
		params.put("text", sentence);
		HttpRequest request = HttpRequest.post(url).send(JsonUtil.JSON_NON_EMPTY_MAPPER.writeValueAsString(params));
		System.out.println(request.code() + "-->" + request.body());

		CutWords cutWords = (CutWords) JsonUtil.JSON_NON_EMPTY_MAPPER.readValue(request.body(), CutWords.class);

		List<String> result = Lists.newArrayListWithCapacity(cutWords.getTokens().size());
		for (Token token : cutWords.getTokens()) {
			result.add(token.getToken());
		}
		return result;
	}
}