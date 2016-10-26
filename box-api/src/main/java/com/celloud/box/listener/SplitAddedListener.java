package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.SplitAddedEvent;
import com.celloud.box.service.SplitQueue;

/**
 * split队列监听器，监听split文件添加到队列的事件，用来触发一次split运行(如果未达到最大同时运行数)
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年10月25日上午11:25:55
 * @version Revision: 1.0
 */
@Component
public class SplitAddedListener implements ApplicationListener<SplitAddedEvent> {
	@Resource
	private SplitQueue queue;
	private static Logger logger = LoggerFactory.getLogger(SplitAddedListener.class);

	@Async
	@Override
	public void onApplicationEvent(SplitAddedEvent event) {
		logger.info("队列添加了新文件：{}", event.getSource());
		queue.split();
	}

}
