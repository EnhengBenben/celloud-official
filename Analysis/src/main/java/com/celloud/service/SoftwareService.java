package com.celloud.service;

import java.util.List;

import com.celloud.sdo.Software;
import com.celloud.service.impl.SoftwareServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(SoftwareServiceImpl.class)
public interface SoftwareService {
	/**
	 * 获取大客户的APP总量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserAPPNum(Integer companyId);

	/**
	 * 获取大客户的APP列表
	 * 
	 * @param company
	 * @return
	 */
	public List<Software> getAppListByBigUser(Integer companyId);
}
