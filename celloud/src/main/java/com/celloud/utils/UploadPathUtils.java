package com.celloud.utils;

import java.io.File;

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

}
