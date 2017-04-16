package cn.blmdz.session;

import java.util.Map;

import cn.blmdz.session.properties.SessionProperties;
import cn.blmdz.session.service.SessionDataSource;
import lombok.Getter;

public class QxxSessionManager {
	private static SessionDataSource dataSource;
	@Getter
	private static SessionProperties properties;
	
	private static final class SingletonHolder {
		private static final QxxSessionManager INSTANCE = init();
		private static QxxSessionManager init() {
			return new QxxSessionManager();
		}
		public static QxxSessionManager getInstance() {
			return INSTANCE;
		}
	}
	
	public Map<String, Object> findSessionById(String prefix, String id) {
		return dataSource.findSessionById(prefix, id);
	}
	public void deletePhysically(String prefix, String id) {
		dataSource.deletePhysically(prefix, id);
	}
	
	public void refreshExpireTime(QxxSession session, int maxInactiveInterval) {
		dataSource.refreshExpireTime(session, maxInactiveInterval);
	}
	
	public boolean save(String prefix, String id, final Map<String, Object> snapshot, final int maxInactiveInterval) {
		return dataSource.save(prefix, id, snapshot, maxInactiveInterval);
	}
	
	public void destroy() {
		dataSource.destroy();
	}
	
	public static QxxSessionManager newInstance(SessionProperties properties, SessionDataSource sessionDataSource) {
		QxxSessionManager.properties = properties;
		QxxSessionManager.dataSource = sessionDataSource;
		return SingletonHolder.getInstance();
	}
	public static QxxSessionManager getInstance() {
		return SingletonHolder.getInstance();
	}

}
