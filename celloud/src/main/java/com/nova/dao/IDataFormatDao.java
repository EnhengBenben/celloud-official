package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DataFormatDaoImpl;
import com.nova.sdo.DataFormat;

@ImplementedBy(DataFormatDaoImpl.class)
public interface IDataFormatDao {
	/**
	 * 获取数据类型列表
	 * 
	 * @return
	 */
	public List<DataFormat> getDataFormatList();

	/**
	 * 根据类型名称获取类型编号
	 * 
	 * @param typeName
	 * @return
	 */
	public int getDataFormatIdByName(String formatName);

	/**
	 * 根据类型编号获取类型名称
	 * 
	 * @param typeName
	 * @return
	 */
	public String getDataFormatNameById(int dataFormatId);
}
