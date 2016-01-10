package com.celloud.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.celloud.constants.Mod;

public class XmlUtil {
    private static final Logger logger = LogManager.getLogger(XmlUtil.class.getName());
	public static Map<String, Map<String, String>> machines = null;
	
	public static void initMachines(){
	    logger.info("初始化集群配置");
	    if(machines==null){
	        machines = getMachines();
	    }
	}
	
	private static Map<String, Map<String, String>> getMachines() {
	    Map<String, Map<String, String>> machines = new HashMap<String, Map<String, String>>();
		InputStream is = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("machine.xml");
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(is);
			Element root = doc.getRootElement(); // 获取根节点
			// 获取根节点下的所有子节点machine
			Iterator<?> machine = root.elementIterator("machine");
			// 遍历machine节点
			while (machine.hasNext()) {
				Element every = (Element) machine.next();
				// 获取machine下的各个属性值
				Map<String, String> map = new HashMap<>();
				map.put(Mod.HOST, every.elementTextTrim(Mod.HOST));
				map.put(Mod.PORT, every.elementTextTrim(Mod.PORT));
				map.put(Mod.USERNAME, every.elementTextTrim(Mod.USERNAME));
				map.put(Mod.PWD, every.elementTextTrim(Mod.PWD));
				machines.put(every.elementTextTrim(Mod.NAME), map);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return machines;
	}

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
