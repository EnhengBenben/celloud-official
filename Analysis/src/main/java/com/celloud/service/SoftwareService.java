package com.celloud.service;

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
}
