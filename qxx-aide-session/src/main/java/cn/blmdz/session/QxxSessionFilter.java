package cn.blmdz.session;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.blmdz.common.util.WebUtil;
import lombok.Setter;

public class QxxSessionFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(QxxSessionFilter.class);
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
	@Setter
	private QxxSessionManager sessionManager;
	
	public QxxSessionFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//init
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof QxxHttpServletRequest) {
			chain.doFilter(request, response);
		} else {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			QxxHttpServletRequest blmdzRequest = new QxxHttpServletRequest(httpRequest, httpResponse);
			blmdzRequest.setMaxInactiveInterval(maxInactiveInterval);
			blmdzRequest.setCookieMaxAge(cookieMaxAge);
			blmdzRequest.setCookieName(cookieName);
			blmdzRequest.setCookieDomain(cookieDomain);
			blmdzRequest.setCookieContextPath(cookieContextPath);
			blmdzRequest.setRedisPrefix(redisPrefix);
			chain.doFilter(blmdzRequest, response);
			QxxSession session = blmdzRequest.currentSession();
			if (session != null) {
				if (session.isInvalid()) {
					//无效
					log.debug("delete login cookie");
					WebUtil.failureCookie(httpRequest, httpResponse,
							cookieName, cookieDomain, cookieContextPath);
				} else if (session.isDirty()) {
					//脏数据
					if(log.isDebugEnabled())
						log.debug("try to flush session to session store");
					Map<String, Object> snapshot = session.snapshot();
					if (sessionManager.save(redisPrefix, session.getId(), snapshot, maxInactiveInterval)) {
						if(log.isDebugEnabled())
							log.debug("succeed to flush session {} to store, key is:{}", snapshot, session.getId());
					} else {
		            	log.error("failed to save session to redis");
		            	WebUtil.failureCookie(httpRequest, httpResponse,
		            			cookieName, cookieDomain, cookieContextPath);
					}
				} else {
					sessionManager.refreshExpireTime(session, maxInactiveInterval);
				}
			}
		}
	}

	@Override
	public void destroy() {
		sessionManager.destroy();
	}

}
