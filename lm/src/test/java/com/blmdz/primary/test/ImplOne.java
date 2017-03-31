package com.blmdz.primary.test;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ImplOne implements PrimaryInterface {

	@Override
	public String show(String content) {
		return "implOne:" + content;
	}

}
