package com.celloud.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.celloud.model.mongo.Behavior;
import com.celloud.service.BehaviorService;
import com.celloud.utils.UserAgentUtil;

/**
 * 用户行为拦截器
 * 
 * @author lin
 * @date 2016年3月15日 下午3:32:15
 */
public class BehaviorInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private BehaviorService service;

    @SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	final Behavior ub = UserAgentUtil.getUserBehavior(request);
        ub.setOperate("");
        ub.setMessage("");
        ub.setLogDate(new Date());
        ub.setMethod(request.getMethod());
        ub.setAction(request.getRequestURI());
        ub.setParam(request.getParameterMap());
        new Runnable() {
			public void run() {
				service.saveUserBehavior(ub);
			}
		}.run();
        return super.preHandle(request, response, handler);
    }
    
}
