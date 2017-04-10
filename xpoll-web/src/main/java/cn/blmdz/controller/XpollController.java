package cn.blmdz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XpollController {

	@RequestMapping(value="/abc", method=RequestMethod.GET)
	public String hello() {
		int[] a = {1};
		System.out.println(a[2]);
		return "sdf";
	}
}
