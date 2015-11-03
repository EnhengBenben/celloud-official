package com.celloud.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.celloud.utils.CustomString;
import com.nova.itext.PGSProjectPDF;
import com.nova.sdo.Data;
import com.nova.utils.FileTools;

/**
 * 
 * @author lin
 * @date 2015-11-2 下午2:39:11
 * @description :项目运行结束后的后续操作
 */
public class RunOverService {

	/**
	 * PGS系列流程项目运行结束后的处理
	 * 
	 * @param appPath
	 * @param appName
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param proDataList
	 * @return
	 */
	public boolean PGS(String appPath, String appName, String appTitle,
			String projectFile, String projectId, List<Data> proDataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		StringBuffer sb = new StringBuffer();
		// 2. 遍历数据列表
		for (Data d : proDataList) {
			String filename = d.getFileName();
			String datakey = d.getDataKey();
			String anotherName = StringUtils.isEmpty(d.getAnotherName()) ? null
					: d.getAnotherName();
			// 3. 为项目PDF生成数据
			sb.append(datakey).append(",")
					.append(CustomString.getBarcode(filename)).append(",")
					.append(anotherName).append(";");

			String finalPath = appPath + datakey;
			String xls = FileTools.fileExist(finalPath, datakey + ".xls",
					"endsWith");
			if (xls.equals("")) {
				xls = FileTools.fileExist(finalPath, "no_enough_reads.xls",
						"endsWith");
			}
			String result[] = null;
			String r[] = FileTools.readFileToString(finalPath + "/" + xls)
					.split("\n");
			if (r.length > 2) {
				result = FileTools.getArray(r, 2).split("\t");
			} else {
				result = FileTools.getLastLine(finalPath + "/" + xls).split(
						"\t");
			}
			resultArray.append(filename).append("\t").append(datakey)
					.append("\t").append(anotherName).append("\t");
			if (result.length == 1) {
				resultArray.append(FileTools.getArray(result, 0)).append("\n");
			} else {
				int titleLength = appTitle.split("\t").length;
				for (int i = 0; i < titleLength - 3; i++) {
					resultArray.append(FileTools.getArray(result, i));
					if (i == titleLength - 4) {
						resultArray.append("\n");
					} else {
						resultArray.append("\t");
					}
				}
			}
		}
		// 2.生成项目pdf
		try {
			PGSProjectPDF.createPDF(appPath, appName, 220, 800, sb.toString(),
					projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

}
