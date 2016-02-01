package com.celloud.service;

import java.util.List;

import com.celloud.model.Upload;

public interface UploadService {
	/**
	 * 修改每个线程位置
	 * 
	 * @param userId
	 * @param dataKey
	 * @param num
	 * @param position
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:35:57
	 */
	public int uploadUpdate(Integer userId, String dataKey, long num, long position);
	
	/**
	 * 新增每个线程位置
	 * 
	 * @param userId
	 * @param dataKey
	 * @param num
	 * @param position
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:44:34
	 */
	public int uploadInsert(Integer userId, String dataKey, long num, long position);
	
	/**
	 * 获取某个数据的所有线程信息
	 * 
	 * @param userId
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:59:18
	 */
	List<Upload> getUploadList( Integer userId,  String dataKey);
}
