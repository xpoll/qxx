package site.blmdz.mobile.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.Maps;

import site.blmdz.mobile.annotation.JsonStreamAliasMappingConfigAnnotationBeanPostProcessor;
import site.blmdz.mobile.enums.Constants;
import site.blmdz.mobile.mode.TestRequest;
import site.blmdz.mobile.mode.TestRequestBody;
import site.blmdz.mobile.mode.TestResponse;

@Service
public class JsonSecurityHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
	ObjectMapper mapper = new ObjectMapper();
	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String url = request.getServletPath();
		String tokenId = (String)request.getParameter(Constants.TOKEN_ID);
		String appType = (String)request.getParameter(Constants.APP_TYPE);
		String userId = (String)request.getParameter(Constants.USER_ID);
		String channelId = (String)request.getParameter(Constants.CHANNEL_ID);
		String content = (String)request.getParameter(Constants.CONTENT);
		String sign = (String)request.getParameter(Constants.SIGN);
		if(JsonStreamAliasMappingConfigAnnotationBeanPostProcessor.getSecurityMap(url)){
			if(request.getSession().getAttribute("isLogin")== null || !(boolean)request.getSession().getAttribute("isLogin")){
				response.getWriter().append("error");
				return false;
			}
		}
		Map<String,Class<?>> xstreamAliasMap = JsonStreamAliasMappingConfigAnnotationBeanPostProcessor.getXStreamAliasMap(url);
		Class<?> clazz = xstreamAliasMap.get(Constants.IN);
		Object obj = mapper.readValue(content, clazz);
		request.setAttribute("res", obj);
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		String url = request.getServletPath();
		System.out.println("postHandle:" + url);
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> map = Maps.newHashMap();
		map.put("name", "cd");
		map.put("ef", "sdf");
//		System.out.println(JSON.toJSONString(map));
//		Map<String, String> m = (Map<String, String>) JSON.parse(JSON.toJSONString(map));
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(new GuavaModule());
		
		
		TestRequest sr = new TestRequest();
		sr.getHead().setAppType("appType1");
		sr.getHead().setTokenId("tokenId1");
		sr.getBody().setAbc("abc1");
		sr.setSign("sdfafdfasdfsafadsfsf23r23r23r");
		System.out.println(mapper.writeValueAsString(sr));
		TestRequest ss = mapper.readValue(mapper.writeValueAsString(sr), TestRequest.class);
		System.out.println(mapper.writeValueAsString(ss));
		
		TestResponse tr = new TestResponse();
		tr.getHead().setAppType("appType2");
		tr.getHead().setTokenId("tokenId2");
		tr.getBody().setAbcd("abcd23");
		tr.setSign("signsfsdfsdfsdfsdfdsf");

		sr.setSign("sdfafdfasdfsafadsfsf23r23r23r");
		System.out.println(mapper.writeValueAsString(tr));
		TestResponse tt = mapper.readValue(mapper.writeValueAsString(tr), TestResponse.class);
		System.out.println(mapper.writeValueAsString(tt));
	}
}