package com.celloud.box.service;

import java.io.File;

import com.celloud.box.model.DataFile;

public interface BoxService {
	/**
	 * 保存文件的基本信息到本地磁盘
	 * 
	 * @param userId
	 * @param name
	 * @param anotherName
	 * @param tagId
	 * @param batch
	 * @param needSplit
	 * @param f
	 * @return
	 */
	public DataFile save(Integer userId, String name, String anotherName, Integer tagId, String batch,
			Integer needSplit, File f);

	/**
	 * 文件到达盒子后，向celloud平台发起创建新文件的请求
	 * 
	 * @param file
	 * @return
	 */
	public DataFile newfile(DataFile file);

	/**
	 * 盒子将文件上传到oss后，向celloud平台发起更新文件的请求
	 * 
	 * @param file
	 * @return
	 */
	public DataFile updatefile(DataFile file);

	/**
	 * 盒子将文件上传到oss后的后续处理<br>
	 * 将文件从uploading转移到uploaded
	 * 
	 * @param file
	 * @return
	 */
	public boolean finish(DataFile file);

	/**
	 * 加载所有的未上传到oss的文件，并将文件加载到上传队列
	 */
	public void loadUnFinishedFiles();

	/**
	 * 校验文件是否可运行split<br>
	 * 如果满足条件，则运行split<br>
	 * 如果不满足条件，则保存信息，等待其他文件
	 * 
	 * @param dataFile
	 */
	public void checkRunSplit(DataFile dataFile);

}
