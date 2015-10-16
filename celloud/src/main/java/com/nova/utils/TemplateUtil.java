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
		template.put(3, "ProjectEnd.txt");
		template.put(4, "DataEnd.txt");
	}

	/**
	 * 读取邮件模板 :0：包含项目报告和数据报告；1：只有数据报告;2：只有项目报告；3:项目结束；4：数据结束;
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
