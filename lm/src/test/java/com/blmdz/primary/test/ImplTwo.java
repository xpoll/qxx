package com.blmdz.primary.test;

import org.springframework.stereotype.Component;

@Component
public class ImplTwo implements PrimaryInterface {

	@Override
	public String show(String content) {
		return "implTwo:" + content;
	}

}
