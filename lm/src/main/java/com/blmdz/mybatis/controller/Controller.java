package com.blmdz.mybatis.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blmdz.mybatis.model.Student;
import com.blmdz.mybatis.service.StudentService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

@RestController
@EnableAutoConfiguration
public class Controller {
	private static final String template = "Hello %s";
	private final AtomicLong counter = new AtomicLong();
	@Autowired
	private StudentService studentService;
	@RequestMapping(value = "/show")
	public String show(
			@RequestParam(value="name", defaultValue="word") String name){
		Student s = new Student();
		s.setFlag(false);
		s.setName("name" + counter.incrementAndGet());
		System.out.println(studentService.Create(s));
		return String.format(template, name) + "! count:" + counter.incrementAndGet();
	}

	@RequestMapping(value = "/hbs")
	public void hbs(
			@RequestParam(value="val", defaultValue="1") String value,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{

		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("index");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
}
