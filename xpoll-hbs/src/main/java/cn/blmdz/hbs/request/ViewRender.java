package cn.blmdz.hbs.request;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.net.MediaType;

import cn.blmdz.hbs.exception.NotFound404Exception;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ViewRender {
	@Autowired
	private PageRender pageRender;

	/**
	 * 进行页面渲染(粉刷)
	 */
	public void view(final String domain, final String path, HttpServletRequest request, HttpServletResponse response,
			final Map<String, Object> context) {
		Supplier<String> getHtml = new Supplier<String>() {
			public String get() {
				return pageRender.render(domain, path, context);
			}
		};
		render(request, response, getHtml);
	}

	/**
	 * 异常处理  response 写页面
	 */
	public void render(HttpServletRequest request, HttpServletResponse response, Supplier<String> getHtml) {
		String html = "";
		try {
			html = Strings.nullToEmpty(getHtml.get());
		} catch (NotFound404Exception e) {
			log.error("render failed, cause:{}", e.getMessage());
			forward("/404", request, response);
			return;
		} catch (Exception e) {
			log.error("render failed, cause:{}", Throwables.getStackTraceAsString(e));
			forward("/500", request, response);
		}
		if (html.startsWith("forward:")) {
			String forwardPath = html.substring("forward:".length());
			forward(forwardPath, request, response);
		} else {
			response.setContentType(MediaType.HTML_UTF_8.toString());
			
			try {response.getWriter().write(html);} catch (Exception e) {}
		}
	}
	
	/**
	 * forward 处理
	 */
	public void forward(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (Exception e) {
			log.warn("error when forward. path:{}, case:{}", path, e);
		}
	}
}