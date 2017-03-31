package com.blmdz.mybatis.test;


import static com.blmdz.mybatis.test.Constants.sessions;
import static com.blmdz.mybatis.test.Constants.users;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;


/**
 * Session监听器
 */
@Component
public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent httpsessionevent) {
		httpsessionevent.getSession().setMaxInactiveInterval(60);
		// 加入到Session集合
		users.add(httpsessionevent.getSession().getId());
		sessions.add("user_session_id:" + users.size());
		// 唤醒全部更新列表
		Util.wakeUpAllThread();
	}

	public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
		// 从Session集合移除
		sessions.remove("user_session_id:" + users.size());
		users.remove(httpsessionevent.getSession().getId());
		// 唤醒全部更新列表
		Util.wakeUpAllThread();

	}

}
