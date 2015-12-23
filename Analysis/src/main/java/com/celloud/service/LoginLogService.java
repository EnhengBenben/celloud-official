package com.celloud.service;

import java.util.Date;
import java.util.List;
import com.celloud.sdo.LoginLog;
import com.celloud.service.impl.LoginLogServiceImpl;
import com.google.inject.ImplementedBy;
@ImplementedBy(LoginLogServiceImpl.class)
public interface LoginLogService {
	/**
	 * 指定周每天登陆次数统计
	 * @param start
	 * @return
	 */
	public List<LoginLog> getUserLoginInWeek(Date start);
	
	
	
	/**
	 * 指定周用户登陆统计
	 * @param start
	 * @return
	 */
	public List<LoginLog> getUserLoginNum(Date start);
	
	/**
	 * 统计周内浏览器使用情况
	 * @param start
	 * @return
	 */
	public List<LoginLog> getBrowserInWeek(Date start);

}
