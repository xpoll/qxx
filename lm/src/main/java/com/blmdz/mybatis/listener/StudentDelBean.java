package com.blmdz.mybatis.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StudentDelBean implements ApplicationContextAware {

	private ApplicationContext app;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.app = applicationContext;
	}
	
	public void delStudent(String name){
		StudentDelEvent del = new StudentDelEvent(app, name);
		app.publishEvent(del);
	}
}
