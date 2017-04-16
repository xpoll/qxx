package cn.blmdz.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;

@SuppressWarnings("deprecation")
public class QxxSession implements HttpSession {
	
	private final long creationAt;
	private volatile long lastAccessedAt;
	private final String id;
	@Getter
	private final String prefix;
	private final HttpServletRequest request;
	private final QxxSessionManager sessionManager;
	private int maxInactiveInterval;
	private final Map<String, Object> attributes = Maps.newHashMapWithExpectedSize(5);
	private final Set<String> deleteAttribute = Sets.newHashSetWithExpectedSize(5);
	private final Map<String, Object> dbSession;
	@Getter
	private volatile boolean invalid;
	@Getter
	private volatile boolean dirty;
	
	public QxxSession(QxxSessionManager sessionManager,
			HttpServletRequest request,
			String prefix,
			String id) {
		this.creationAt = System.currentTimeMillis();
		this.lastAccessedAt = creationAt;
		this.id = id;
		this.prefix = prefix;
		this.request = request;
		this.sessionManager = sessionManager;
		this.dbSession = loadDbSession();
	}
	
	private Map<String, Object> loadDbSession() {
		return sessionManager.findSessionById(prefix, id);
	}

	@Override
	public long getCreationTime() {
		return creationAt;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedAt;
	}

	@Override
	public ServletContext getServletContext() {
		return request.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return request.getSession().getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		checkValid();
		return attributes.containsKey(name)?
				attributes.get(name):
					(deleteAttribute.contains(name)?
							null:
								dbSession.get(name));
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		checkValid();
		Set<String> names = Sets.newHashSet(dbSession.keySet());
		names.addAll(attributes.keySet());
		names.removeAll(deleteAttribute);
		return Collections.enumeration(names);
	}

	@Override
	public String[] getValueNames() {
		checkValid();
		Set<String> names = Sets.newHashSet(dbSession.keySet());
		names.addAll(attributes.keySet());
		names.removeAll(deleteAttribute);
		return names.toArray(new String[names.size()]);
	}

	@Override
	public void setAttribute(String name, Object value) {
		checkValid();
		if(value != null) {
			this.attributes.put(name, value);
			this.deleteAttribute.remove(name);
		} else {
			this.attributes.remove(name);
			this.deleteAttribute.add(name);
		}
		this.dirty = true;
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		checkValid();
		this.deleteAttribute.add(name);
		this.attributes.remove(name);
		this.dirty = true;
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
		this.dirty = true;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#invalidate()
	 * 无效
	 */
	@Override
	public void invalidate() {
		this.invalid = true;
		this.dirty = true;
		sessionManager.deletePhysically(prefix, id);
	}

	@Override
	public boolean isNew() {
		return true;
	}
	
	public boolean isValid() {
		return invalid;
	}
	
	public Map<String, Object> snapshot() {
		Map<String, Object> snap = Maps.newHashMap();
		snap.putAll(attributes);
		snap.putAll(dbSession);
		for (String key : deleteAttribute) {
			snap.remove(key);
		}
		return snap;
	}

	protected void checkValid() throws IllegalStateException {
		Preconditions.checkState(!invalid);
	}
}
