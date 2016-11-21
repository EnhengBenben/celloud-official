package com.celloud.box.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UploadPath {
	private static String PATH = null;
	private static final char S = File.separatorChar;

	/**
	 * 获取盒子存储文件的根目录
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return PATH != null ? PATH : '/' == S ? "/share/data/upload" : System.getProperty("user.home");
	}

	/**
	 * 获取未上传到OSS的文件在盒子上存储的路径
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUploadingPath(Integer userId) {
		return getRootPath() + S + "uploading" + S + DateUtils.formartToday("yyyyMMdd") + S + userId + S;
	}

	/**
	 * 获取未上传到OSS的文件在盒子上存储的根路径
	 * 
	 * @return
	 */
	public static String getUploadingPath() {
		return getRootPath() + S + "uploading" + S;
	}

	/**
	 * 获取已上传到OSS的文件在盒子上存储的路径
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUploadedPath(Integer userId) {
		return getRootPath() + S + "uploaded" + S + userId + S + DateUtils.formartToday("yyyyMMdd") + S;
	}

	/**
	 * 获取已上传到OSS的文件在盒子上存储的路径
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUploadedPath() {
		return getRootPath() + S + "uploaded" + S;
	}

	/**
	 * split校验文件的文件名
	 * 
	 * @param userId
	 * @param batch
	 * @param name
	 * @return
	 */
	public static String getSplitNewName(Integer userId, String batch, String name) {
		return MD5Util.getMD5(userId + "---" + batch + "---" + name);
	}

	/**
	 * 获取split流程的输出结果路径
	 * 
	 * @param dataKey
	 * @return
	 */
	public static String getSplitOutputPath(Integer userId, String batch, String name) {
		return getRootPath() + S + "split" + S + "output" + S + getSplitNewName(userId, batch, name) + S;
	}

	/**
	 * 获取存放list文件的路径
	 * 
	 * @param dataKey
	 * @return
	 */
	public static String getSplitListPath(Integer userId, String batch, String name) {
		return getRootPath() + S + "split" + S + "lists" + S + getSplitNewName(userId, batch, name) + ".txt";
	}

	/**
	 * split校验文件的存储路径
	 * 
	 * @param userId
	 * @param batch
	 * @param name
	 * @return
	 */
	public static String getSplitCheckingPath(Integer userId, String batch, String name) {
		return getRootPath() + S + "split" + S + "checking" + S + getSplitNewName(userId, batch, name) + ".txt";
	}

	/**
	 * 获取文件在盒子内存储的唯一文件名
	 * 
	 * @param userId
	 * @param name
	 * @param lastModifiedDate
	 * @param size
	 * @return
	 */
	public static String getUniqueName(Integer userId, String name, long lastModifiedDate, long size) {
		return MD5Util.getMD5(userId + "--" + name + "--" + lastModifiedDate + "--" + size) + "_" + name;
	}

	/**
	 * 获取文件在oss上存储的objectKey<br>
	 * 命名规则:{userId}/{yyyyMMdd}/{dataKey}.{extName}
	 * 
	 * @param userId
	 * @param dataKey
	 * @param ext
	 * @return
	 */
	public static String getObjectKey(Integer userId, String dataKey, String ext) {
		return "file/"+userId + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + dataKey
				+ (ext.startsWith(".") ? ext : "." + ext);
	}

	public static String getRandomName(String uniqueName) {
		return (new Random().nextInt(9000) + 1000) + "_" + uniqueName;
	}

	public static void setRootPath(String path) {
		PATH = path;
	}

}
