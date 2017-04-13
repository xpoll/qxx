package cn.blmdz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageErrorFilter implements Filter {
	
	private Boolean debugger;
	
	public PageErrorFilter(Boolean debugger) {
		this.debugger = debugger;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, javax.servlet.FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if(debugger)
			System.out.println(httpRequest.getRequestURI() + " : " +httpResponse.getStatus());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}


}