package com.blmdz.bt.control;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Controller {
	private static final String template = "Hello %s";
	private final AtomicLong counter = new AtomicLong();
	@RequestMapping(value = "/show")
	public String show(
			@RequestParam(value="name", defaultValue="word") String name){
		return String.format(template, name) + "! count:" + counter.incrementAndGet();
	}
}
