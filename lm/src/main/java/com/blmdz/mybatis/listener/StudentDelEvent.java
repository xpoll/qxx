package com.blmdz.mybatis.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 减少学生的监听事件(监听的事件对象)
 */
public class StudentDelEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private String name;

	public StudentDelEvent(Object source) {
		super(source);
	}
	public StudentDelEvent(Object source, String name) {
		super(source);
		this.name = name;
	}
	
	public String getStudentName() {
		return name;
	}

}
