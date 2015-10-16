package com.celloud.service;

import java.util.List;

import com.celloud.sdo.Software;
import com.celloud.service.impl.SoftwareServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:39:27
 * @version Revision: 1.0
 */
@ImplementedBy(SoftwareServiceImpl.class)
public interface SoftwareService {
	/**
	 * 根据数据类型查询APP列表
	 * 
	 * @param formatId
	 * @return
	 */
	public List<Software> getAppsByFormat(Integer formatId);

	/**
	 * 查询所有的APP信息
	 * 
	 * @return
	 */
	public List<Software> getAllSoftware();

	/**
	 * 根据编号获取软件信息
	 * 
	 * @param softwareId
	 * @return
	 */
	// TODO 尽量不要根据id检索某个属性，直接单查就好了
	public String getAppNameById(Long softwareId);
}
