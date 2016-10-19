package com.celloud.box.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		return getRootPath() + S + userId + S + DateUtils.formartToday("yyyyMMdd") + S;
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
	public static String getUniqueName(Integer userId, String name, Date lastModifiedDate, long size) {
		return MD5Util.getMD5(userId + "--" + name + "--" + lastModifiedDate.getTime() + "--" + size) + "_" + name;
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
		return userId + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + dataKey
				+ (ext.startsWith(".") ? ext : "." + ext);
	}

	public static void setRootPath(String path) {
		PATH = path;
	}

}
