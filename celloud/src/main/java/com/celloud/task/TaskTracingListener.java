package com.celloud.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.common.mq.AloneMessageListener;
import com.celloud.common.mq.entity.Message;
import com.celloud.common.mq.entity.TaskTracingMessage;
import com.celloud.common.mq.entity.TrackStatus;
import com.celloud.service.impl.TaskTracingService;
import com.celloud.utils.SpringTool;

/**
 * 任务跟踪的监听器，处理任务的失败或结束事件
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年3月8日下午1:43:39
 * @version Revision: 1.0
 */
public class TaskTracingListener implements AloneMessageListener {
	private TaskTracingService service = SpringTool.getBean(TaskTracingService.class);
	private static Logger logger = LoggerFactory.getLogger(TaskTracingListener.class);

	@Override
	public boolean consume(Message message) {
		TaskTracingMessage tmsg = message.getData(TaskTracingMessage.class);
		TrackStatus status = tmsg.getStatus();
		if (TrackStatus.ERR.equals(status) || TrackStatus.FINISH.equals(status)) {
			boolean result = service.finish(tmsg);
			logger.info("处理任务的事件{}: taskId={} , appCode={}, status={}", result ? "成功" : "失败", tmsg.getTaskId(),
					tmsg.getAppCode(), status);
		}
		return true;
	}

}
