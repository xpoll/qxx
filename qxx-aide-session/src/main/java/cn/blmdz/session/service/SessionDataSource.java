package cn.blmdz.session.service;

import java.util.Map;

import cn.blmdz.session.QxxSession;

/**
 * SessionDataSource
 * Session 数据源
 * @author lm
 * @date 2016年11月1日 上午12:20:29
 */
public interface SessionDataSource {
	/**
	 * 根据ID查找Session
	 * @author lm
	 * @date 2016年11月1日 上午12:20:27
	 * @param prefix
	 * @param id
	 * @return Map
	 */
	Map<String, Object> findSessionById(String prefix, String id);
	
	/**
	 * 刷新到期时间
	 * @author lm
	 * @date 2016年11月1日 上午12:22:39
	 * @param session 复写Session
	 * @param maxInactiveInterval 超时时间
	 */
	void refreshExpireTime(QxxSession session, int maxInactiveInterval);
	
	/**
	 * 刷新到期时间
	 * @author lm
	 * @date 2016年11月1日 上午12:23:57
	 * @param prefix 前缀
	 * @param id ID
	 * @param maxInactiveInterval 超时时间
	 */
	void refreshExpireTime(String prefix, String id, int maxInactiveInterval);
	
	/**
	 * 物理删除
	 * @author lm
	 * @date 2016年11月1日 上午12:25:17
	 * @param prefix 前缀
	 * @param id ID
	 */
	void deletePhysically(String prefix, String id);
	
	/**
	 * 保存
	 * @author lm
	 * @date 2016年11月1日 上午12:27:02
	 * @param prefix 前缀
	 * @param id ID
	 * @param snapshot 快照
	 * @param maxInactiveInterval 超时时间
	 */
	boolean save(String prefix, String id, final Map<String, Object> snapshot, final int maxInactiveInterval);
	
	/**
	 * 销毁
	 * @author lm
	 * @date 2016年11月1日 上午12:27:44 void
	 */
	void destroy();
}
