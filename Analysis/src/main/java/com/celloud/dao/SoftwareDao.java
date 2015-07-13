package com.celloud.dao;

import com.celloud.dao.impl.SoftwareDaoImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(SoftwareDaoImpl.class)
public interface SoftwareDao {
	/**
	 * 获取大客户的APP总量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserAPPNum(Integer companyId);
}
