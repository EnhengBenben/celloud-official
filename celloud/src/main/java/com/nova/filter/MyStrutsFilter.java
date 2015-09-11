package com.nova.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MyStrutsFilter extends StrutsPrepareAndExecuteFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	// 不过滤的url
	String url = request.getRequestURI();
	if ("/celloud/socketServlet".equals(url)
		|| "/celloud/testServlet".equals(url)
		|| "/celloud/uploadService".equals(url)
		|| "/celloud/plugins/ueditor/jsp/imageUp.jsp".equals(url)
		|| "/celloud/plugins/ueditor/jsp/fileUp.jsp".equals(url)) {
	    chain.doFilter(req, resp);
	} else {
	    super.doFilter(req, resp, chain);
	}
    }
}
