package com.blmdz.mybatis.test;

import static com.blmdz.mybatis.test.Constants.sessionId;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@RestController
@EnableAutoConfiguration
public class ControllerTest {


	

	@RequestMapping(value = "/c")
	public void c(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("chat_c");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
	@RequestMapping(value = "/s")
	public void s(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		TemplateLoader load = new ClassPathTemplateLoader("/hbs/", ".hbs");
		Handlebars hbs = new Handlebars(load);
		Template tem = hbs.compile("chat_s");
		response.getWriter().write(tem.apply(context));
		response.getWriter().flush();
	}
	@PreDestroy
	public void destory(){
		System.out.println("destory");
	}
	@RequestMapping(value = "/c_conn")
	public void c_conn(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context
			) throws IOException{
	
		response.setContentType("text/event-stream");//设置事件流  
        response.setCharacterEncoding("UTF-8");//设置编码
        String sID = request.getSession().getId();

		if(sessionId.isEmpty()){
			List<String> c = Lists.newArrayList();
			List<String> s = Lists.newArrayList();
			sessionId.put("c", c);
			sessionId.put("s", s);
		}
		boolean isHave = false;
		for (String str : sessionId.get("c")) {
			if(Objects.equal(request.getSession().getId(), str))
				isHave = true;
		}
		if(!isHave) {
			sessionId.get("c").add(sID);
			List<String> ms = Lists.newArrayList();
			Constants.msg.put("0" + "_" + sID, ms);
		}
		String s_str = "";
		for (String str : Constants.msg.keySet()) {
			if(str.split("_")[1].equals(sID)){
				s_str = Constants.msg.get(str).toString();
			}
		}
		response.getWriter().write("data: " + s_str + "\n\n");
	}
	@RequestMapping(value = "/s_conn")
	public void s_conn(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context) throws IOException{
		response.setContentType("text/event-stream");//设置事件流  
        response.setCharacterEncoding("UTF-8");//设置编码  
        String sID = request.getSession().getId();
		if(sessionId.isEmpty()){
			List<String> c = Lists.newArrayList();
			List<String> s = Lists.newArrayList();
			sessionId.put("c", c);
			sessionId.put("s", s);
		}
		boolean isHave = false;
		for (String str : sessionId.get("s")) {
			if(Objects.equal(request.getSession().getId(), str))
				isHave = true;
		}

		if(!isHave) {
			sessionId.get("s").add(request.getSession().getId());
			List<String> ms = Lists.newArrayList();
			Constants.msg.put(sID +  "_" + "0", ms);
		}
		for (String str : Constants.msg.keySet()) {
			if(str.split("_")[0].equals(sID)){
				response.getWriter().write("data: " + Constants.msg.get(str).toString());
			}
		}
	}
	@RequestMapping(value = "/s_send")
	public void s_send(
			String massage,
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context
			) throws IOException{
		String sID = request.getSession().getId();
		for (String str : Constants.msg.keySet()) {
			if(str.split("_")[0].equals(sID)){
				Constants.msg.get(str).add(massage);
				response.getWriter().write("data: " + Constants.msg.get(str).toString());
			}
		}
	}
	@RequestMapping(value = "/c_send")
	public void c_send(
			String massage,
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> context
			) throws IOException{
		String sID = request.getSession().getId();
		for (String str : Constants.msg.keySet()) {
			if(str.split("_")[1].equals(sID)){
				Constants.msg.get(str).add(massage);
				response.getWriter().write("data: " + Constants.msg.get(str).toString());
			}
		}
	}
}
//response.getWriter().flush();
//if(!isHave) {
//	sessionId.get("c").add(request.getSession().getId());
//	//分配客服
//	if(sessionId.get("s").isEmpty()){
//		response.getWriter().write("data: " + "当前无客服人员" + "\n\n");
//	}else{
//		List<String> free = Lists.newArrayList();
//		for (String str_1 : sessionId.get("s")) {
//			boolean no_b = true;
//			for (String str_2 : ser.keySet()) {
//				if(Objects.equal(str_1, str_2))
//					no_b = false;
//			}
//			if(no_b)
//				free.add(str_1);
//		}
//		if(!free.isEmpty()){
//			int i = (int)(Math.random()*free.size())+1;
//			String str = free.get(i) + "_" + request.getSession().getId();
//			// 加入映射
//			Thread t = Thread.currentThread();
//			t.setName(str);
//			Constants.sessionThread.put(str, t);
//			List<String> lmsg = Lists.newArrayList();
//			//聊天内容加入
//			Constants.msg.put(str, lmsg);
//			// 当前线程等待
//			try {
//				synchronized (t) {
//					t.wait();
//				}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				System.out.println("error");
//			}
////			// 返回消息
////			Util.out(response);
//		}
//	}
//}else{
//	List<String> thread = Lists.newArrayList();
//	thread.addAll(Constants.sessionThread.keySet());
//	Thread th = null;
//	for (int i = 0; i < thread.size(); i++) {
//		if(thread.get(i).split("_")[2].equals(request.getSession().getId()))
//			th = Constants.sessionThread.get(thread.get(i));
//		
//	}
//	if(th == null){
//		response.getWriter().write("data: " + "连接超时,请刷新" + "\n\n");
//		response.getWriter().flush();
//	}else{
//		try {
//			synchronized (th) {
//				th.wait();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			System.out.println("error");
//		}
//	}
//}
