package cn.blmdz.search;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import cn.blmdz.util.JsonUtil;

public class S01 {
	static String host = "127.0.0.1";
	static int port = 9200;
	static String index = "lm";
	static String type = "item";
	static ESClient client = null;
	static {
		client = new ESClient(host, port);
		System.out.println(client.health());
		client.createIndexIfNotExists(index);
		try {
			client.createMappingIfNotExists(index, type, Resources.toString(Resources.getResource("lm_mapping.json"), Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
//		client.index(index, type, "", Resources.toString(Resources.getResource("index.json"), Charsets.UTF_8));
		Response response = client.search(index, type, "{\"query\":{\"match\":{\"name\":\"吖嗱\"}}}");
		System.out.println(JsonUtil.JSON_NON_EMPTY_MAPPER.writeValueAsString(response));
		response = client.search(index, type, "{\"query\":{\"match\":{\"name\":\"明天\"}},\"highlight\":{\"fields\":{\"name\":{}}}}");
		System.out.println(JsonUtil.JSON_NON_EMPTY_MAPPER.writeValueAsString(response));
		
	}
}
