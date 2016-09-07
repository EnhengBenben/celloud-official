package com.celloud.box.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadPath {
	private static final String PATH = "/share/data/upload/";
	private static final char S = File.separatorChar;

	private static String getRootPath() {
		if ('/' == S) {
			return PATH;
		} else {
			return System.getProperty("user.home");
		}
	}

	public static String getPath(Integer userId) {
		return getRootPath() + S + userId + S + new SimpleDateFormat("yyyyMMdd").format(new Date()) + S;
	}

	public static String getUniqueName(Integer userId, String name, Date lastModifiedDate, long size) {
		return MD5Util.getMD5(userId + "--" + name + "--" + lastModifiedDate.getTime() + "--" + size) + "_" + name;
	}

	public static String getObjectKey(Integer userId, String dataKey, String ext) {
		return userId + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + dataKey
				+ (ext.startsWith(".") ? ext : "." + ext);
	}

	public static void main(String[] args) {
		System.out.println(getRootPath());
	}
}
