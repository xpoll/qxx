package cn.blmdz.hbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("api")
public class Testc {

	@RequestMapping(value="test", method=RequestMethod.POST)
	public String test(HttpServletRequest request, @RequestBody String json) {
		System.out.println();
		return json;
	}

	@RequestMapping(value="teacher", method=RequestMethod.POST)
	public Teacher p (@RequestBody Teacher teacher) {
		
		return teacher;
	}
	@RequestMapping(value="teacher1", method=RequestMethod.POST)
	public Teacher p1 (Teacher teacher) {
		
		return teacher;
	}
}
@Data
class Teacher {
	private Long id;
	private String name;
}
