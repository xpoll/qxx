package cn.blmdz.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.google.common.collect.Lists;

import cn.blmdz.hunt.common.util.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Test1 {

	public static void main(String[] args) {
		try {
			Client client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
			System.out.println("start");
			delete(client);
			// ######### 创建
			create(client);
			// ######### 查询
//			query(client);
			// ######### 更新
//			updateqRequest(client);
//			updateDoc1(client);
//			updateDoc2(client);
//			updateScript(client);
			// ######### 更新或创建
//			upsert(client);
			// ######### 更新或创建
//			delete(client);

			client.close();
			System.out.println(client);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(Client client) {
		DeleteResponse request = client.prepareDelete("blog", "article", "AVtb_Nkvsu2DtjJP6TWM").get();
		if (request.isFound()) {
			System.out.println("删除成功");
		}
	}
	
	
	public static void upsert(Client client) throws IOException, InterruptedException, ExecutionException {
		IndexRequest indexRequest = new IndexRequest("blog", "article", "10").source(
				XContentFactory.jsonBuilder().startObject().field("title", "Git安装").field("content", "学习目标 git。。。").endObject());

		UpdateRequest uRequest2 = new UpdateRequest("blog", "article", "10").doc(
				XContentFactory.jsonBuilder().startObject().field("title", "Git安装").field("content", "学习目标 git。。。").endObject())
                .upsert(indexRequest);
		
        client.update(uRequest2).get();
	}
	
	public static void updateScript(Client client) throws IOException {
		client.prepareUpdate("blog", "article", "AVtbrmmLto60729jEv_1")
		.setScript(new Script("ctx._source.content = \"更改3\"", ScriptType.INLINE, null, null))
		.get();
	}

	public static void updateDoc1(Client client) throws IOException {
		client.prepareUpdate("blog", "article", "AVtbrmmLto60729jEv_1")
		.setDoc(XContentFactory.jsonBuilder().startObject().field("content", "更改2").endObject())
		.get();
	}
	
	public static void updateDoc2(Client client) throws IOException {
		client.prepareUpdate("blog", "article", "AVtbrmmLto60729jEv_1")
		.setDoc(JsonMapper.nonDefaultMapper().toJson(new Blog(8, "other知识", "2016-06-19", "更改4")))
		.get();
	}

	public static void updateqRequest(Client client) throws InterruptedException, ExecutionException, IOException {
		UpdateRequest update = new UpdateRequest("blog", "article", "AVtbrmmLto60729jEv_1")
				.doc(XContentFactory.jsonBuilder().startObject().field("content", "更改1").endObject());
		client.update(update).get();
	}
	
	public static void query(Client client) {
//		QueryBuilder qb1 = QueryBuilders.termQuery("title", "hibernate");
		QueryBuilder qb2 = QueryBuilders.multiMatchQuery("git", "title","content");
		SearchResponse response = client.prepareSearch("blog").setTypes("article").setQuery(qb2).execute().actionGet();
		SearchHits hits = response.getHits();
		if (hits.totalHits() > 0) {
            for (SearchHit hit : hits) {
                System.out.println("score:"+hit.getScore()+":\t"+hit.getSource());
            }
        } else {
            System.out.println("搜到0条结果");
        }
	}
	
	public static void create(Client client) {
		BulkRequestBuilder bulkRequest=client.prepareBulk();
		
		for (String string : list()) {
			bulkRequest.add(client.prepareIndex("test", "article").setSource(string));
		}
		
		BulkResponse response = bulkRequest.get();
		
		if (!response.hasFailures()) {
			System.out.println("创建成功!");
		}
	}

	public static List<String> list() {
		List<String> list = Lists.newArrayList();
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(2, "Java中泛型的介绍与简单使用", "2016-06-19", "学习目标 掌握泛型的产生意义...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(3, "SQL基本操作", "2016-06-19", "基本操作：CRUD ...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(4, "Hibernate框架基础", "2016-06-19", "Hibernate框架基础...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(5, "Git基本知识git", "2016-06-19", "Shell是什么...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(6, "C++基本知识", "2016-06-19", "Shell是什么...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(7, "Mysql基本知识", "2016-06-19", "git是什么...")));
		list.add(JsonMapper.nonDefaultMapper().toJson(new Blog(8, "other知识", "2016-06-19", "git是什么...")));
		return list;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Blog {
		private Integer id;
		private String title;
		private String postTime;
		private String content;
	}
}
