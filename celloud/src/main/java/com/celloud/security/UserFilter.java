package com.celloud.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.utils.UserAgentUtil;

public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {
    private static Logger logger = LoggerFactory.getLogger(UserFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return super.onAccessDenied(req, res);
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        logger.warn("用户非法访问：{}", UserAgentUtil.getUrl(request));
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            // 如果是ajax请求响应头会有，x-requested-with
            response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
        }
        return false;
    }
}
