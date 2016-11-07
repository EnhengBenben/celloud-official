package com.celloud.box.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.SplitRunErrorEvent;

/**
 * split队列监听器，监听split运行失败的事件，文件运行split失败，将文件重新加入到队列中
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年10月25日上午11:27:10
 * @version Revision: 1.0
 */
@Component
public class SplitRunErrorListener implements ApplicationListener<SplitRunErrorEvent> {
	@Async
	@Override
	public void onApplicationEvent(SplitRunErrorEvent event) {
		// TODO Auto-generated method stub

	}

}
