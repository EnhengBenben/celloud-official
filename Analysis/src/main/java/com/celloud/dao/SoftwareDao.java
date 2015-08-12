package com.celloud.dao;

import java.util.List;

import com.celloud.dao.impl.SoftwareDaoImpl;
import com.celloud.sdo.Software;
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

	/**
	 * 获取大客户的APP列表
	 * 
	 * @param company
	 * @return
	 */
	public List<Software> getAppListByBigUser(Integer companyId);
}
