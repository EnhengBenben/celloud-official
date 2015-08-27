package com.nova.tools.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class XMLUtil {
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
}