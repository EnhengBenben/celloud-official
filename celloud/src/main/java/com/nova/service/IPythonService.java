package com.nova.service;

import com.google.inject.ImplementedBy;
import com.nova.service.impl.PythonServiceImpl;

/**
 * @Description:为 Python 客户端提供的接口服务
 * @author lin
 */
@ImplementedBy(PythonServiceImpl.class)
public interface IPythonService {

	/**
	 * 客户端登陆方法
	 * 
	 * @param username
	 *            ：用户名
	 * @param pwd
	 *            ：密码
	 * @return：登陆失败返回 0 ，登陆成功则返回 userId
	 */
	public String login(String username, String pwd);

	/**
	 * 获取客户上传文件的总大小
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总大小，单位是B
	 */
	public long getSize(int id);

	/**
	 * 断点续传时获取该文件的大小
	 * 
	 * @param id
	 * @param dataKey
	 * @return
	 */
	public long getDataSize(int id, String dataKey);

	/**
	 * 获取客户上传文件的总个数
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总个数
	 */
	public long getNumber(int id);

	/**
	 * 文件上传前获取 DataKey
	 * 
	 * @param id
	 *            ：用户id
	 * @param fileName
	 *            ：文件名
	 * @param md5
	 *            ：MD5值
	 * @return：返回该文件的唯一标识：dataKey
	 */
	public String getDataKey(Integer id, String fileName, String md5);

	/**
	 * 文件上传结束后保存进数据库
	 * 
	 * @param dataKey
	 *            ：文件唯一标示
	 * @return：true 上传成功，false 上传失败
	 */
	public String uploaded(String dataKey);

	/**
	 * 获取客户端版本
	 * 
	 * @return
	 */
	public String getClientVersion();

	/**
	 * 保存已上传的文件大小
	 * 
	 * @param dataKey
	 * @param size
	 *            ：已上传的文件大小
	 * @return
	 */
	public long saveDataSize(String dataKey, long size);
}