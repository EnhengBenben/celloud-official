package com.celloud.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
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
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	@Resource
	private BehaviorService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Behavior behavior = UserAgentUtil.getUserBehavior(request);
		// TODO 待积攒一定数据后下面两个字段用于绑定方法对应的按钮／功能
		behavior.setOperate("");
		behavior.setMessage("");
		behavior.setLogDate(new Date());
		behavior.setMethod(request.getMethod());
		behavior.setAction(request.getRequestURI());
		behavior.setParam(request.getParameterMap());
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long endTime = System.currentTimeMillis();// 2、结束时间
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		behavior.setConsumeTime(consumeTime);
		service.saveUserBehavior(behavior);
		super.afterCompletion(request, response, handler, ex);
	}

}
