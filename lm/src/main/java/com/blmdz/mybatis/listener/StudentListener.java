package com.blmdz.mybatis.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 事件的监听器（负责处理接收到的监听事件）
 */
@Component
public class StudentListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof StudentAddEvent)
			System.out.println("StudentAddEvent:" + ((StudentAddEvent)event).getStudentName());
		else if(event instanceof StudentDelEvent)
			System.out.println("StudentAddEvent:" + ((StudentDelEvent)event).getStudentName());
	}
}
