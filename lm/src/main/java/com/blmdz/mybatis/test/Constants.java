package com.blmdz.mybatis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 公共属性
 */
public class Constants {

	/**
	 * Session集合
	 */
	public static List<String> users = new ArrayList<String>();
	
	public static List<String> sessions = new ArrayList<String>();
	

	/**
	 * 消息集合
	 */
	public static List<String> messages = new ArrayList<String>();

	/**
	 * 会话线程映射 
	 * Key:SessionId 
	 * Value:Thread
	 */
	public static Map<String, Thread> sessionThreadMapping = new HashMap<String, Thread>();
	
	
	/**
	 * sessionId
	 * key c/s
	 * value List<String>
	 */
	public static Map<String, List<String>> sessionId = Maps.newHashMap();
	
	/**
	 * 聊天对应关系
	 * key 为客服sessionID
	 */
	public static Map<String, String> ser = Maps.newHashMap();
	
	/**
	 * 会话线程映射 
	 * key: s_session_id + _ + c_session_id
	 * Value:Thread
	 */
	public static Map<String, Thread> sessionThread = Maps.newHashMap();
	
	/**
	 * 聊天内容
	 * key: s_session_id + _ + c_session_id
	 */
	public static Map<String, List<String>> msg = Maps.newHashMap();
}
