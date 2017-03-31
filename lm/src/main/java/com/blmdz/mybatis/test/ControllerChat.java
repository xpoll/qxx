package com.blmdz.mybatis.test;

import static com.blmdz.mybatis.test.Constants.sessions;
import static com.blmdz.mybatis.test.Constants.users;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

@RestController
@EnableAutoConfiguration
public class ControllerChat {

	@RequestMapping(value = "/test1")
	public void test1(
			@RequestParam(value="val", defaultValue="1") String value,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{

		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("index");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
	@RequestMapping(value = "/test1_conn")
	public void test1_conn(
			@RequestParam(value="val", defaultValue="1") String value,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		try {
		      Thread.sleep(5000);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		    
		response.getWriter().write("helloworld<br>");
	}
	//index3
	@RequestMapping(value = "/test3")
	public void test3(
			@RequestParam(value="val", defaultValue="1") String value,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{

		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("index3");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/init")
	public void init(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		HttpSession session = request.getSession();
		System.out.println(session.getId());
		Util.out(response);
	}
	
	@RequestMapping(value = "/ref")
	public void ref(
			@RequestParam(value="val", defaultValue="1") String value,
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{

		HttpSession session = request.getSession();
		System.out.println(session.getId());
		Thread t = Thread.currentThread();
		t.setName(session.getId());
		// 加入映射
		Constants.sessionThreadMapping.put(session.getId(), t);
		// 当前线程等待
		try {
			synchronized (t) {
				t.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 返回消息
		Util.out(response);
	}
	@RequestMapping(value = "/send")
	public void send(
			@RequestParam(value="message", defaultValue="1") String message,
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		HttpSession session = request.getSession();
		System.out.println(session.getId());

		int i = users.indexOf(request.getSession().getId());
		message = sessions.get(i) + ":" + message;
		Constants.messages.add(message);
		// 唤醒全部更新列表
		Util.wakeUpAllThread();
		// 返回消息
		Util.out(response);
	}
	//index4
	@RequestMapping(value = "/test4")
	public void test4(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("index4");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
	@RequestMapping(value = "/test4_conn")
	public void test4_conn(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
        response.setContentType("text/event-stream");//设置事件流  
        response.setCharacterEncoding("UTF-8");//设置编码  
        //获取最新时间并返回  
        String string = new Date().toString();  
        System.out.println(string);  
        // send SSE  
        response.getWriter().write("data: " + string + "\n\n");  
        try {   //设置间隔时间  
            Thread.sleep(1000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
	}
}
