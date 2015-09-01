package com.nova.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {

    public static String writeXML(String path) {
	String context = null;
	try {
	    context = FileUtils.readFileToString(new File(path));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	if (context == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer("<table>");
	String lines[] = context.split("\n");
	for (String line : lines) {
	    sb.append("<tr>");
	    String datas[] = line.split("\t");
	    for (String data : datas) {
		sb.append("<td>").append(data).append("</td>");
	    }
	    sb.append("</tr>");
	}
	sb.append("</table>");
	return sb.toString();
    }

    /**
     * 将数据库中的xml格式的项目报告解析成List<List<String>>
     * 
     * @param context
     * @return
     */
    public static List<List<String>> readXML(String context) {
	List<List<String>> listXML = new ArrayList<List<String>>();
	Document doc = null;
	try {
	    // 将字符串转为XML
	    doc = DocumentHelper.parseText(context);
	    // 获取根节点
	    Element root = doc.getRootElement();
	    // 获取根节点下的子节点data
	    Iterator<?> dataList = root.elementIterator("data");
	    // 遍历data节点
	    while (dataList.hasNext()) {
		List<String> listData = new ArrayList<String>();
		Element data = (Element) dataList.next();
		// 拿到data节点下的子节点val值
		Iterator<?> valList = data.elementIterator("val");
		// 遍历val节点值
		while (valList.hasNext()) {
		    Element val = (Element) valList.next();
		    listData.add(val.getTextTrim());
		}
		listXML.add(listData);
	    }
	} catch (DocumentException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listXML;
    }

}
