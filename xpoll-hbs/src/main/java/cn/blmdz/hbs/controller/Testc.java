package cn.blmdz.hbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class Testc {

	@RequestMapping(value="test", method=RequestMethod.POST)
	public String test(HttpServletRequest request, @RequestBody String json) {
		System.out.println();
		return json;
	}
}
