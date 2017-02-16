package com.celloud.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.celloud.common.mq.MessageConsumer;
import com.celloud.common.mq.kafka.KafkaConsumerConfig;
import com.celloud.common.mq.kafka.KafkaConsumerImpl;
import com.celloud.model.mongo.Behavior;
import com.celloud.service.BehaviorService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CustomStringUtils;
import com.celloud.utils.UserAgentUtil;

/**
 * 用户行为拦截器
 * 
 * @author lin
 * @date 2016年3月15日 下午3:32:15
 */
public class BehaviorInterceptor extends HandlerInterceptorAdapter {
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	private NamedThreadLocal<Behavior> userBehavior = new NamedThreadLocal<Behavior>("StopWatch-UserBehavior");
	private Logger logger = LoggerFactory.getLogger(BehaviorInterceptor.class);
	@Resource
	private BehaviorService service;
	@Resource
	private MessageConsumer consumer;

	/**
	 * action的前置处理，用来获取操作的用户相关信息
	 * 
	 * <br>
	 * <br>
	 * <strong>用户相关信息不能放到action的后置处理中，因为action的后置处理中，response有可能已经commit了(如，
	 * 用户主动退出)</strong>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		openMessageReceiver(request);
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		Behavior behavior = UserAgentUtil.getUserBehavior(request);
		behavior.setLogDate(new Date());
		behavior.setMethod(request.getMethod());
		behavior.setAction(CustomStringUtils.substringBefore(request.getRequestURI(), ";jsessionid"));
		behavior.setQueryString(request.getQueryString());
		userBehavior.set(behavior);
		return super.preHandle(request, response, handler);
	}

	private void openMessageReceiver(HttpServletRequest request) {
		if (consumer == null || !(consumer instanceof KafkaConsumerImpl) || consumer.isStarted()) {
			return;
		}
		String localAddr = request.getLocalAddr();
		if ("0:0:0:0:0:0:0:1".equals(localAddr)) {
			localAddr = "127.0.0.1";
		}
		String groupId = localAddr + ":" + request.getLocalPort();
		KafkaConsumerConfig kafkaConfig = (KafkaConsumerConfig) this.consumer.getConfig();
		kafkaConfig.setGroupId(groupId);
		this.consumer.startup();
	}

	/**
	 * action后置处理，主要用来获取action的ActionLog注解信息和action的执行时间
	 * 
	 * <br>
	 * <br>
	 * <strong>用户相关信息不能放到action的后置处理中，因为action的后置处理中，response有可能已经commit了(如，
	 * 用户主动退出)</strong>
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Behavior behavior = userBehavior.get();
		if (handler instanceof HandlerMethod) {
			ActionLog log = ((HandlerMethod) handler).getMethodAnnotation(ActionLog.class);
			if (log != null) {
				behavior.setOperate(log.value());
				behavior.setMessage(log.button());
				logger.debug("operate={};button={}", log.value(), log.button());
			}
			String beanName = ((HandlerMethod) handler).getBeanType().getName();
			String methodName = ((HandlerMethod) handler).getMethod().getName();
			behavior.setHandler(beanName + "." + methodName + "()");
		} else {
			behavior.setHandler(handler.toString());
		}
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long endTime = System.currentTimeMillis();// 2、结束时间
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		behavior.setConsumeTime(consumeTime);
		service.saveUserBehavior(behavior);
		super.afterCompletion(request, response, handler, ex);
	}

}
