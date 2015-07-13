package com.nova.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @Description:为文件上传客户端提供的接口服务
 * @author lin
 * @date 2014-10-10 下午3:42:59
 */
@WebService
public interface IUploadService {

	/**
	 * 客户端登陆方法
	 * 
	 * @param username
	 *            ：用户名
	 * @param pwd
	 *            ：密码
	 * @return：返回 0 登陆失败，登陆成功则返回 userId + "," + uuid，uuid从客户端打开页面有用
	 */
	@WebMethod
	public String login(String username, String pwd);

	/**
	 * 获取客户上传文件的总大小
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总大小，单位是B
	 */
	@WebMethod
	public long getSize(int id);

	/**
	 * 获取客户上传文件的总个数
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总个数
	 */
	@WebMethod
	public long getNumber(int id);

	/**
	 * 文件上传前的初始化工作
	 * 
	 * @param id
	 *            ：用户id
	 * @param fileName
	 *            ：文件名
	 * @return：返回该文件的唯一标识：dataKey
	 */
	@WebMethod
	public String init(Integer id, String fileName);

	/**
	 * 上传文件的具体方法
	 * 
	 * @param dataKey
	 *            ：文件唯一标示
	 * @param buffer
	 *            ：二进制流
	 * @param length
	 *            ：buffer的有效长度
	 * @param position
	 *            ：起始位置
	 * @param blocks
	 *            ：块数（这个参数实际上并没有使用）
	 * @return：true 上传成功，false 上传失败
	 */
	@WebMethod
	public boolean saveFile(String dataKey, byte[] buffer, int length,
			long position, int blocks);

	/**
	 * 解压缩算法（该方法实际上并没有使用）
	 * 
	 * @param dictionary
	 *            ：压缩字典，格式为: val:key;val:key;
	 * @param datakey
	 *            :唯一标示
	 * @return
	 */
	@WebMethod
	public boolean deco(String dictionary, String dataKey);

	/**
	 * 文件上传结束后，通过校验md5来检验文件是否上传成功
	 * 
	 * @param dataKey
	 *            ：文件唯一标示
	 * @param dataKey
	 *            ：文件md5值
	 * @return：true 上传成功，false 上传失败
	 */
	@WebMethod
	public boolean checkMD5(String dataKey,String md5,boolean isUnZip);

	/**
	 * 根据datakye获取文件长度（应该没有用到）
	 * 
	 * @param dataKey
	 *            ：文件唯一标示
	 * @return：已经保存的文件长度
	 */
	@WebMethod
	public long getLength(String dataKey);

	/**
	 * 返回服务器上的msi文件的版本号，如果和客户端版本号不一致，则客户端自动下载更新
	 * 
	 * @return
	 */
	@WebMethod
	public String getVersion();

	/**
	 * 客户端自动更新（将服务器上的msi文件生成二进制流返回）
	 * 
	 * @return
	 */
	@WebMethod
	public byte[] getClient();
}