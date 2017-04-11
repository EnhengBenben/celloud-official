package com.celloud.utils;

import java.io.File;

import com.celloud.constants.ConstantsData;

/**
 * 生成上传文件的各种路径的工具类
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年11月11日上午11:26:34
 * @version Revision: 1.0
 */
public class UploadPathUtils {
	private static final String S = String.valueOf(File.separatorChar);
	private static final StringBuilder root = new StringBuilder(
			PropertiesUtil.bigFilePath.endsWith(S) ? PropertiesUtil.bigFilePath : (PropertiesUtil.bigFilePath + S));

	public static String getLocalPath(Integer userId, String dataKey, String ext) {
		return root.append(userId).append(S).append(DateUtil.getDateToString("yyyyMMdd")).append(S).append(dataKey)
				.append(ext).toString();
	}

	public static String getListPathInOSS() {
		return ConstantsData.getOfsPath() + "list/" + DateUtil.getDateToString("yyyyMMdd") + "/";
	}

	public static String getObjectKeyByPath(String path) {
		return path.startsWith(ConstantsData.getOfsPath()) ? path.substring(ConstantsData.getOfsPath().length()) : path;
	}

	public static String getOutPathInOSS(Integer userId, Integer appId) {
		return ConstantsData.getOfsPath() + "output/" + userId + "/" + appId + "/";
	}

	public static String getOutPathInOSS() {
		return ConstantsData.getOfsPath() + "output/";
	}

	public static String getObjectKey(Integer userId, String fileDataKey, String extName) {
		return "file/" + userId + "/" + DateUtil.getDateToString("yyyyMMdd") + "/" + fileDataKey + extName;
	}
}
