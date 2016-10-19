package com.nova.tools.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nova.tools.constant.Mod;

public class XMLUtil {
	public static Map<String, Map<String, String>> machines = new HashMap<String, Map<String, String>>();
	
	static{
	    getMachines();
	}

	public static String writeXML(String path) {
		StringBuffer sb = new StringBuffer("<table>");
		if (new File(path).exists()) {
			String context = null;
			try {
				context = FileUtils.readFileToString(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String lines[] = context.split("\n");
			for (String line : lines) {
				sb.append("<tr>");
				String datas[] = line.split("\t");
				for (String data : datas) {
					sb.append("<td>").append(data).append("</td>");
				}
				sb.append("</tr>");
			}
		}
		sb.append("</table>");
		return sb.toString();
	}

	public static void getMachines() {
		InputStream is = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream("machine.xml");
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(is);
			Element root = doc.getRootElement(); // 获取根节点
			// 获取根节点下的所有子节点machine
			Iterator<?> machine = root.elementIterator("machine");
			// 遍历machine节点
			while (machine.hasNext()) {
				Element pEvery = (Element) machine.next();
				// 获取machine下的各个属性值
				String name = pEvery.elementTextTrim("name");
				String host = pEvery.elementTextTrim("host");
				String port = pEvery.elementTextTrim("port");
				String username = pEvery.elementTextTrim("username");
				String pwd = pEvery.elementTextTrim("pwd");
				Map<String, String> map = new HashMap<>();
				map.put(Mod.HOST, host);
				map.put(Mod.PORT, port);
				map.put(Mod.USERNAME, username);
				map.put(Mod.PWD, pwd);
				machines.put(name, map);
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
	}

}