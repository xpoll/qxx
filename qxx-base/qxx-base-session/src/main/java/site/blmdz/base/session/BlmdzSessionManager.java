package site.blmdz.base.session;

import java.util.Map;

import lombok.Getter;
import site.blmdz.base.session.redis.SessionRedisSource;
import site.blmdz.base.session.service.SessionDataSource;
import site.blmdz.base.session.service.SessionIdGenerator;
import site.blmdz.base.session.util.Config;
import site.blmdz.base.session.util.SessionIdGeneratorDefault;

public class BlmdzSessionManager {
	private final SessionDataSource dataSource;
	@Getter
	private final SessionIdGenerator sessionIdGenerator;
	@Getter
	private static Config config;
	
	public BlmdzSessionManager(final Config config) {
		sessionIdGenerator = new SessionIdGeneratorDefault();
		if("redis".equals(config.getSource())){
			dataSource = new SessionRedisSource(config);
		} else {
			throw new IllegalStateException("constructor error: not support source type :" + config.getSource());
		}
	}
	private static final class SingletonHolder {
		private static final BlmdzSessionManager INSTANCE = init();
		private static BlmdzSessionManager init() {
			return new BlmdzSessionManager(config);
		}
		public static BlmdzSessionManager getInstance() {
			return INSTANCE;
		}
	}
	
	public Map<String, Object> findSessionById(String prefix, String id) {
		return dataSource.findSessionById(prefix, id);
	}
	public void deletePhysically(String prefix, String id) {
		dataSource.deletePhysically(prefix, id);
	}
	
	public void refreshExpireTime(BlmdzSession session, int maxInactiveInterval) {
		dataSource.refreshExpireTime(session, maxInactiveInterval);
	}
	
	public boolean save(String prefix, String id, final Map<String, Object> snapshot, final int maxInactiveInterval) {
		return dataSource.save(prefix, id, snapshot, maxInactiveInterval);
	}
	
	public void destroy() {
		dataSource.destroy();
	}
	
	public static BlmdzSessionManager newInstance(Config config) {
		BlmdzSessionManager.config = config;
		return SingletonHolder.getInstance();
	}
	public static BlmdzSessionManager getInstance() {
		return SingletonHolder.getInstance();
	}

}
