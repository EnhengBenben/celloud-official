package com.nova.tools.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @Description:该访问过滤器用于禁止非celloud的请求访问Tools页面
 * @author lin
 * @date 2013-12-3 下午2:26:37
 */
public class VisitFilter implements Filter {
    private static Logger log = Logger.getLogger(VisitFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;
	//TODO https 无法获取referer，该判断已经无法使用
	// String referer = request.getHeader("referer");
	// boolean isAllow = referer == null ? true :
	// !referer.contains("celloud");
	// isAllow 为空，说明是地址栏直接输入的地址
	// if (isAllow) {
	// log.error("非法访问！试图访问：" + request.getRequestURI());
	// response.sendRedirect(request.getContextPath() + "/error.html");
	// }
	chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}