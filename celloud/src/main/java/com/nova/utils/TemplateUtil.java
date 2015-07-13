package com.nova.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class TemplateUtil {
	private static Map<Integer, String> template = new HashMap<Integer, String>();
	static {
		template.put(0, "emailTemplate.txt");
	}

	/**
	 * * 读取邮件模板 * * @param emailType *
	 */
	public static String readTemplate(Integer emailType) {
		String path = TemplateUtil.class.getResource(
				"/" + template.get(emailType)).getPath();
		File file = new File(path);
		String source = null;
		try {
			source = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			System.out.println("读取邮件模板失败" + e);
		}
		return source;
	}
}
