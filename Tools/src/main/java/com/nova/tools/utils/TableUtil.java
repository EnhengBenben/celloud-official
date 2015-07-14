package com.nova.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TableUtil {
	public static Logger log = Logger.getLogger(TableUtil.class);

	/**
	 * 表格头，带三个bootstrap 样式
	 */
	public static String tableHead = "<table class='table table-bordered table-condensed'>";

	/**
	 * 将文件内容转化成 Table
	 * 
	 * @rule 文件内容的每一行即对应 Table 的每一行；文件内容每一行以 Tab 键间隔作为每一个单元格
	 * @param filePath
	 *            ：文件路径
	 * @param isTitle
	 *            ：第一行是否是表头
	 * @return
	 */
	public static String simpleTable(String filePath, boolean isTitle) {
		StringBuffer sb = new StringBuffer();
		File file = new File(filePath);
		if (file.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				log.error("要读取的文件不存在：" + filePath);
			}
			String line = "";
			try {
				sb.append(tableHead);
				if (isTitle) {
					sb.append("<thead>");
					line = br.readLine();
					sb.append("<tr>");
					sb.append(sepLine(line, "th"));
					sb.append("</tr>");
					sb.append("</thead>");
				}
				sb.append("<tbody>");
				while ((line = br.readLine()) != null) {
					if ("".equals(line.trim())) {
						continue;
					}
					sb.append("<tr>");
					sb.append(sepLine(line, "td"));
					sb.append("</tr>");
				}
				sb.append("</tbody></table>");
			} catch (IOException e1) {
				log.error("读取文件失败：" + e1);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 使用分隔符按照tab切分某一行
	 * 
	 * @param line
	 *            ：要切分的行
	 * @param separator
	 *            ：切割符，支持th，td，p等
	 * @return
	 */
	public static String sepLine(String line, String separator) {
		StringBuffer sb = new StringBuffer();
		String detail[] = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, "\t");
		for (String string : detail) {
			sb.append("<").append(separator).append(">").append(string)
					.append("</").append(separator).append(">");
		}
		return sb.toString();
	}

	/**
	 * 将文件的哪些列的值取出
	 * 
	 * @param path
	 *            ：文件路径
	 * @param columns
	 *            ：列编号，从0开始，可间隔
	 * @return
	 */
	public static String txtGetComumns(String path,
			List<Integer> columns) {
	StringBuffer sb = new StringBuffer();
		if (new File(path).exists()) {
			String context = null;
			try {
				context = FileUtils.readFileToString(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String lines[] = context.split("\n");
			for (int j = 1; j < lines.length; j++) {
				String datas[] = lines[j].split("\t");
				sb.append("<div class='m-boxCon'><div class='title'>").append(j);
				sb.append("、").append(datas[0].trim()).append("</div>");
				sb.append("<div><p>功能描述：").append(datas[16].trim()).append("</p>");
				sb.append("<p>与疾病的相关性：").append(datas[15].trim()).append("</p></div></div>");
			}
		}
		return sb.toString();
	}

	/**
	 * 将文件的哪些列封装成table
	 * 
	 * @param path
	 *            ：文件路径
	 * @param columns
	 *            ：列编号，从0开始，可间隔，第一行做表头
	 * @return
	 */
	public static String simpleTable(String path, List<Integer> columns,int start,String title,boolean isNum) {
		StringBuffer sb = new StringBuffer(tableHead);
		if (new File(path).exists()) {
			String context = null;
			try {
				context = FileUtils.readFileToString(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(title!=null){
				sb.append(title);
			}
			String lines[] = context.split("\n");
			for (int j = start; j < lines.length; j++) {
				sb.append("<tr>");
				String datas[] = lines[j].split("\t");
				if(isNum){
					sb.append("<td>").append(j) .append("</td>");
				}
				for (int i = 0; i < datas.length; i++) {
					if (columns.contains(i)) {
						if (j == 0) {
							sb.append("<th>").append(datas[i].trim())
									.append("</th>");
						} else {
							sb.append("<td>").append(datas[i].trim())
									.append("</td>");
						}
					}
				}
				sb.append("</tr>");
			}
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 将一个文件每行作为一个单元格内容，以num个单元格为一行封装成table
	 * 
	 * @param path
	 *            ：文件路径
	 * @param num
	 *            ：多少列，num>0
	 * @return
	 */
	public static String simpleTable(String path, Integer num) {
		StringBuffer sb = new StringBuffer(tableHead);
		sb.append("<tr>");
		if (new File(path).exists()) {
			String context = null;
			try {
				context = FileUtils.readFileToString(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String lines[] = context.split("\n");
			for (int j = 0; j < lines.length; j++) {
				sb.append("<td>").append(lines[j].trim()).append("</td>");
				if (j > 0 && j % num == num - 1) {
					sb.append("</tr><tr>");
				}
			}
		}
		sb.append("</tr></table>");
		return sb.toString();
	}

	/**
	 * list转换成table
	 * 
	 * @param list
	 * @param hasHead
	 * @return
	 */
	public static String listToTable(List<String> list, boolean hasHead) {
		StringBuffer sb = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String[] line_i = list.get(i).split("\t");
				StringBuffer td = new StringBuffer();
				for (String line : line_i) {
					td.append(sepLine(line, i == 0 && hasHead ? "th" : "td"));
				}
				sb.append("<tr>").append(td.toString()).append("</tr>");
			}
		}
		return sb.toString();
	}

	/**
	 * list转换成table
	 * 
	 * @param list
	 * @param hasHead
	 * @return
	 */
	public static String listToTableHasTbody(List<String> list, boolean hasHead) {
		StringBuffer sb = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String[] line_i = list.get(i).split("\t");
				StringBuffer td = new StringBuffer();
				if (i == 0 && hasHead) {
					sb.append("<thead>");
				} else if (i == 0 && !hasHead) {
					sb.append("<tbody>");
				}
				for (String line : line_i) {
					td.append(sepLine(line, i == 0 && hasHead ? "th" : "td"));
				}
				sb.append("<tr>").append(td.toString()).append("</tr>");
				if (i == 0 && hasHead) {
					sb.append("</thead>");
				} else if (i == 0 && !hasHead) {
					sb.append("</tbody>");
				}
			}
		}
		return sb.toString();
	}
}