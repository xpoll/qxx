package cn.blmdz.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.blmdz.common.util.IdGeneratorUtil;
import cn.blmdz.common.util.WebUtil;
import lombok.Setter;

public class QxxHttpServletRequest extends HttpServletRequestWrapper {
	private Logger log = LoggerFactory.getLogger(QxxHttpServletRequest.class);
	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final QxxSessionManager sessionManager;
	private QxxSession blmdzSession;
	
	@Setter
	private int maxInactiveInterval;
	@Setter
	private int cookieMaxAge;
	@Setter
	private String cookieName;
	@Setter
	private String cookieDomain;
	@Setter
	private String cookieContextPath;
	@Setter
	private String redisPrefix;
	
	public QxxHttpServletRequest(HttpServletRequest request,
			HttpServletResponse response) {
		super(request);
		this.request = request;
		this.response = response;
		this.sessionManager = QxxSessionManager.getInstance();
	}

    @Override
    public HttpSession getSession(boolean create) {
        return doGetSession(create);
    }

    @Override
    public HttpSession getSession() {
        return doGetSession(true);
    }
    
    private HttpSession doGetSession(boolean create) {
    	if (blmdzSession == null) {
    		Cookie cookie = WebUtil.findCookie(this, cookieName);
    		if (cookie != null && !"__DELETED__".equals(cookie.getValue())) {
    			log.debug("Find session`s id from cookie.[{}]", cookie.getValue());
    			blmdzSession = buildSession(redisPrefix, cookie.getValue(), false);
    		} else {
    			blmdzSession = buildSession(true);
    		}
    	} else {
    		log.debug("Session[{}] was existed.", blmdzSession.getId());
    	}
    	return blmdzSession;
    }

    private QxxSession buildSession(String prefix, String sessionId, boolean cookie) {
    	blmdzSession = new QxxSession(sessionManager, request, prefix, sessionId);
    	blmdzSession.setMaxInactiveInterval(maxInactiveInterval);
    	if (cookie) {
    		WebUtil.addCookie(request, response,
    				cookieName, sessionId, 
    				cookieDomain, cookieContextPath,
    				cookieMaxAge, true);
    	}
    	return blmdzSession;
    }
    
    private QxxSession buildSession(boolean create) {
    	if (create) {
    		blmdzSession = buildSession(redisPrefix, IdGeneratorUtil.generatorId(request), true);
    		log.debug("Build new session[{}].", blmdzSession.getId());
    		return blmdzSession;
    	}
    	return null;
    }

	public QxxSession currentSession() {
		return blmdzSession;
	}

}
