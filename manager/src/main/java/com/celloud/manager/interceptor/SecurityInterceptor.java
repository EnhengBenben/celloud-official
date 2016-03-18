package com.celloud.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.celloud.manager.constants.Constants;
import com.celloud.manager.model.User;
import com.celloud.manager.utils.UserAgentUtil;

/**
 * 登录拦截器，校验用户是否已登录
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午3:59:41
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        if (user == null) {
            logger.warn("用户非法访问：{}", UserAgentUtil.getUrl(request));
            // throw new SecurityException();
            if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
                // 如果是ajax请求响应头会有，x-requested-with
                response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
