package com.celloud.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.utils.UserAgentUtil;

public class UserAuthFilter extends UserFilter {
	private static Logger logger = LoggerFactory.getLogger(UserAuthFilter.class);

	/**
	 * 判断是否允许访问，如果返回false，则会跳到onAccessDenied处理
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object obj) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return super.isAccessAllowed(req, res, obj);
		}
		HttpServletRequest request = (HttpServletRequest) req;
		logger.warn("用户非法访问：{}", UserAgentUtil.getUrl(request));
		return false;
	}

	/**
	 * 被拒绝后的处理
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getRequestURI();
        if ("XMLHttpRequest"
                .equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            // 如果是ajax请求响应头会有，x-requested-with
            response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
        } else if (url.contains("client")) {
            WebUtils.issueRedirect(request, response, "/client.html");
        } else {
			WebUtils.issueRedirect(request, response, "/sessionTimeOut.html");
        }
        return false;
	}
}
