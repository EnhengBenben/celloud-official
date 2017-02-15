package com.celloud.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @Description:不需要运行，JAVA通过输入文件直接生成报告
 * @author lin
 * @date 2017年2月9日 下午2:23:18
 */
public interface DirectReportService {
	/**
	 * @Description:FSocg流程运行
	 * @param userId
	 * @param appId
	 * @param list
	 * @param path
	 * @param projectId
	 * @author lin
	 * @date 2017年2月10日 上午10:49:15
	 */
	@Async
	public void fsocg(Integer userId, Integer appId, String list, String path, Integer projectId);
}
