package cn.blmdz.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;

public class ESUtil {

	/**
	 * 判断索引是否存在
	 */
	public static boolean isIndexExists(Client client, String indices) {
		IndicesExistsResponse response = client.admin().indices().exists(new IndicesExistsRequest(indices)).actionGet();
		System.out.println("判断索引是否存在:" + response.isExists());
		return response.isExists();
	}
	
	/**
	 * 创建索引
	 */
	public static boolean createIndex(Client client, String indices) {
		if(isIndexExists(client, indices)) {
			System.out.println("创建索引失败:已存在");
			return false;
		}
		
		CreateIndexResponse response = client.admin().indices().create(new CreateIndexRequest(indices)).actionGet();
		System.out.println("创建索引:" + response.isAcknowledged());
		return response.isAcknowledged();
	}
	
	/**
	 * 删除索引
	 */
	public static boolean deleteIndex(Client client, String indices) {
		if(!isIndexExists(client, indices)) {
			System.out.println("删除索引失败:不存在");
			return false;
		}
		
		DeleteIndexResponse response = client.admin().indices().prepareDelete(indices).get();
		System.out.println("删除索引:" + response.isAcknowledged());
		return response.isAcknowledged();
	}
}
