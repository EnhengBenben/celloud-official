package com.celloud.box.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadPath {
	private static final String PATH = "/share/data/upload";
	private static final char S = File.separatorChar;

	/**
	 * 获取盒子存储文件的根目录
	 * 
	 * @return
	 */
	private static String getRootPath() {
		if ('/' == S) {
			return PATH;
		} else {
			return System.getProperty("user.home");
		}
	}

	/**
	 * 获取文件在盒子上存储的路径
	 * 
	 * @param userId
	 * @return
	 */
	public static String getPath(Integer userId) {
		return getRootPath() + S + userId + S + new SimpleDateFormat("yyyyMMdd").format(new Date()) + S;
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

	public static void main(String[] args) {
		System.out.println(getRootPath());
	}
}
