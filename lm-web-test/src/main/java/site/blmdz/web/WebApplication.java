package site.blmdz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import site.blmdz.base.session.BlmdzHttpServletRequest;

@RestController
@SpringBootApplication
public class WebApplication {
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WebApplication.class);
		application.run(args);
	}

	@RequestMapping(value="", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String simple(
			HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println(request);
//		System.out.println(response);
//		System.out.println(request.getSession());
		System.out.println(request instanceof BlmdzHttpServletRequest);
		return "hello world";
	}

	@RequestMapping(value="a", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String simplea(
			HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("a", "a");
		request.getSession().setAttribute("aa", "aa");
//		System.out.println(request);
//		System.out.println(response);
//		System.out.println(request.getSession());
		System.out.println(request instanceof BlmdzHttpServletRequest);
		return "hello world";
	}
}
