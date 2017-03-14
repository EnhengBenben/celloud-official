package com.celloud.service.impl;

import com.celloud.common.mq.entity.TaskTracingMessage;

public interface TaskTracingService {
	/**
	 * 处理taskTracing中的task异常或者结束事件
	 * 
	 * @param message
	 * @return
	 */
	boolean finish(TaskTracingMessage message);

}
