package com.nova.tools.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * @Description:模板读取工具类
 * @author lin
 * @date 2013-7-30 下午1:05:51
 */
public class TemplateUtils {

    private static Map<Integer, String> template = new HashMap<Integer, String>();
    static {
	template.put(0, "BothTemplate.txt");
	template.put(1, "DataTemplate.txt");
	template.put(2, "ProjectTemplate.txt");
	template.put(3, "ProjectEnd.txt");
	template.put(4, "DataEnd.txt");
	template.put(5, "Exome_SNV.html");
	template.put(6, "sample.html");
	template.put(7, "SNP.html");
    }

    /**
     * 读取邮件模板
     * 
     * @param emailType
     *            ：0：包含项目报告和数据报告；1：只有数据报告;2：只有项目报告；3:项目结束；4：数据结束;5：外显子主体页面模板；6
     *            外显子样本模板；7：SNP 页面模板
     * @return
     */
    public static String readTemplate(Integer emailType) {
	String path = TemplateUtils.class.getResource(
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