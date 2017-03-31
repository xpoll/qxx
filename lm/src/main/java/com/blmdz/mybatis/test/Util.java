package com.blmdz.mybatis.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import flexjson.JSONSerializer;

/**
 * 工具类
 */
public class Util {

	/**
	 * 唤醒全部
	 */
	public static void wakeUpAllThread() {
		Iterator<Map.Entry<String, Thread>> iterator = Constants.sessionThreadMapping
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Thread> entry = iterator.next();
			Thread thread = entry.getValue();
			synchronized (thread) {
				thread.notify();
			}
		}
	}

	/**
	 * 唤醒指定
	 * 
	 * @param sessionId
	 */
	public static void wakeUpAllThread(String sessionId) {
		Iterator<Map.Entry<String, Thread>> iterator = Constants.sessionThreadMapping
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Thread> entry = iterator.next();
			if (sessionId.equals(entry.getKey())) {
				Thread thread = entry.getValue();
				synchronized (thread) {
					thread.notify();
				}
			}
		}
	}

	/**
	 * 输出用户列表
	 * 
	 * @param response
	 * @throws IOException
	 */
	public static void out(HttpServletResponse response) throws IOException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("users", Constants.sessions);
		map.put("messages", Constants.messages);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(map);
//		new JSONSerializer().serialize(map)
		out.flush();
		out.close();
	}

}
