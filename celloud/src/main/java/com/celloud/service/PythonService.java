package com.celloud.service;

/**
 * 为 Python 客户端提供的接口服务
 * 
 * @author lin
 * @date 2016年1月28日 下午3:31:33
 */
public interface PythonService {
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
	 * @param userId
	 * @return:客户上传文件的总大小，单位是B
	 * @author lin
	 * @date 2016年1月28日下午3:38:32
	 */
	public Long getSize(Integer userId);

	/**
	 * 获取客户上传文件的总个数
	 * 
	 * @param userId
	 * @return：客户上传文件的总个数
	 * @author lin
	 * @date 2016年1月28日下午3:39:27
	 */
	public Long getNumber(Integer userId);

	/**
	 * 获取客户端版本
	 * 
	 * @return
	 */
	public String getClientVersion();

	/**
	 * 文件上传前获取 DataKey
	 * 
	 * @param userId
	 *            ：用户id
	 * @param fileName
	 *            ：文件名
	 * @param md5
	 *            ：MD5值
	 * @return：返回该文件的唯一标识：dataKey
	 */
	public String getDataKey(Integer userId, String fileName, String md5);

	/**
	 * 客户端多线程上传，修改每个线程位置（无则新增）
	 * 
	 * @param userId
	 * @param dataKey
	 * @param num
	 * @param position
	 * @return
	 * @author lin
	 * @date 2016年1月28日下午3:35:46
	 */
	public Integer threadUpdate(Integer userId, String dataKey, long num, long position);

	/**
	 * 客户端多线程上传，获取断点
	 * 
	 * @param userId
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年1月28日下午3:34:49
	 */
	public String threadRead(Integer userId, String dataKey);

	/**
	 * 文件上传结束后保存进数据库
	 * 
	 * @param dataKey
	 *            ：文件唯一标示
	 * @return：true 上传成功，false 上传失败
	 */
	public String uploaded(String dataKey);

	/**
	 * 文件上传成功后发送邮件
	 * 
	 * @param userId
	 * @param fileName
	 * @param dataKey
	 */
	public void sendEmail(Integer userId, String fileName, String dataKey);
	
    /**
     * 保存已上传的文件大小
     * 
     * @param dataKey
     * @param size
     *            ：已上传的文件大小
     * @return
     */
    public long saveDataSize(String dataKey, long size);
    
    /**
     * 断点续传时获取该文件的大小
     * 
     * @param userId
     * @param dataKey
     * @return
     * @author lin
     * @date 2016年1月31日下午9:31:31
     */
    public long getDataSize(Integer userId, String dataKey);
}
