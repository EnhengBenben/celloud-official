package com.celloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.celloud.constants.Constants;
import com.celloud.model.User;
import com.celloud.utils.BehaviorUtil;

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
        String url = request.getRequestURL()+"?"+request.getQueryString()+"&behavior:"+BehaviorUtil.getBehavior(request);
        if(user==null){
            logger.warn("用户非法访问：{}",url);
        //    throw new SecurityException();
        }
        long time = System.currentTimeMillis();
        boolean result = super.preHandle(request, response, handler);
        time = System.currentTimeMillis()-time;
        if(time>20){
            logger.info("请求响应时间过长({})：{}",time+"ms",url);
        }
        return result;
    }
}
