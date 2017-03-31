package site.blmdz.mobile.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import site.blmdz.mobile.annotation.JsonStreamAliasMapping;
import site.blmdz.mobile.mode.TestRequest;
import site.blmdz.mobile.mode.TestResponse;

@Controller
@RequestMapping("/api")
public class MobileController {

	@RequestMapping(value="/test", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonStreamAliasMapping(security=false, url="/api/test", incls = TestRequest.class, outcls = TestResponse.class)
	@ResponseBody
	public String getTest(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		TestRequest req = (TestRequest) request.getAttribute("req");
		System.out.println("enter .. controller api test");
		return "api test";
	}
}
