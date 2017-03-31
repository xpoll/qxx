package com.blmdz.mybatis.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StudentAddBean implements ApplicationContextAware {

	private ApplicationContext app;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.app = applicationContext;
	}
	
	public void addStudent(String name){
		StudentAddEvent add = new StudentAddEvent(app, name);
		app.publishEvent(add);
	}
}
