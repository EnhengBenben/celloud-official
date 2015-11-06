package com.celloud.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.nova.utils.FileTools;
import com.nova.utils.TemplateUtil;
import com.nova.utils.ZipUtil;

/**
 * @Description:SNP静态页面
 * @author lin
 * @date 2013-12-11 下午3:08:35
 */
public class HBV_SNP {
	private static Logger log = Logger.getLogger(HBV_SNP.class);

	/**
	 * HBV_SNP html 页面的生成方法
	 * 
	 * @param path
	 *            结果路径
	 * @param fileName
	 *            文件名称
	 * @param cssPath
	 *            css/img 等文件的路径
	 * @step1 在项目下创建目录，名称：HBV_SNP，即总目录
	 * @step2 将项目结果文件拷贝到上述目录下的 result 目录下
	 * @step3 将需要的css/js/img 拷贝到 support 目录下
	 * @step4 生成总页面 HBV_SNP.html
	 * @step5 压缩总目录，名称：总目录.zip
	 * @return
	 */
	public static String createHtml(String path, String fileName, String cssPath) {
		// step1
		path = path.endsWith("/") ? path : path + "/";
		String resultPath = path + "HBV_SNP/result";
		new File(resultPath).mkdirs();
		// step2
		try {
			FileUtils.copyDirectory(new File(path), new File(resultPath));
			FileUtils.forceDelete(new File(resultPath + "/HBV_SNP"));
		} catch (IOException e) {
			log.error("拷贝项目结果失败" + e);
		}
		// step3
		String supportPath = path + "HBV_SNP/support";
		new File(supportPath).mkdirs();
		try {
			FileUtils.copyDirectory(new File(cssPath), new File(supportPath));
		} catch (IOException e) {
			log.error("拷贝SNP支持文件失败" + e);
		}
		// step4
		Map<String, String> map = getInfo(path);
		Set<Map.Entry<String, String>> set = map.entrySet();

		String html = TemplateUtil.readTemplate(7);
		html = html.replace("#fileName", fileName);
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			html = html.replace("#" + entry.getKey(), entry.getValue());
		}

		String totalHtml = path + "HBV_SNP/HBV_SNP.html";
		FileTools.createFile(totalHtml);
		FileTools.appendWrite(totalHtml, html);
		// step5
		try {
			ZipUtil.ZIP(path + "HBV_SNP", path + "HBV_SNP.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "down";
	}

	private static Map<String, String> getInfo(String path) {
		String snpPath = path + "SVG";
		Map<String, String> resultMap = new HashMap<String, String>();
		// 位点图片
		String number[] = { "169", "173", "180", "181", "184", "194", "202",
				"204", "236", "250" };
		for (String num : number) {
			resultMap.put("svg" + num,
					FileTools.fileExist(snpPath, num + ".png", "endsWith"));
		}
		// 其他
		List<String> other = FileTools
				.fileSearch(snpPath, "new.png", "endWith");
		StringBuffer sb = new StringBuffer();
		for (String img : other) {
			sb.append("<img title='" + img
					+ "' style=\"padding-left: 30px;\" src='./result/SVG/"
					+ img + "' height=\"150px;\" width=\"150px;\">");
		}
		resultMap.put("other", sb.toString());
		// 所有检索结果（大图）
		String all1 = FileTools.fileExist(snpPath, "1_all.png", "endsWith");
		String all2 = FileTools.fileExist(snpPath, "2_all.png", "endsWith");
		String all3 = FileTools.fileExist(snpPath, "3_all.png", "endsWith");
		String all4 = FileTools.fileExist(snpPath, "4_all.png", "endsWith");
		String all5 = FileTools.fileExist(snpPath, "5_all.png", "endsWith");
		resultMap.put("listAll1", all1);
		resultMap.put("listAll2", all2);
		resultMap.put("listAll3", all3);
		resultMap.put("listAll4", all4);
		resultMap.put("listAll5", all5);

		String result = FileTools.readAppoint(snpPath + "/Report.txt");
		resultMap.put("result", result.split("Other")[0]);
		String snpType = FileTools.readAppoint(snpPath + "/type.txt");
		resultMap.put("type", snpType);
		return resultMap;
	}
}