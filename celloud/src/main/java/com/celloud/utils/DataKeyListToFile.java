package com.celloud.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.celloud.constants.SparkPro;
import com.celloud.model.mysql.DataFile;

/**
 * 
 * @author lin
 * @date 2015-12-10 下午2:53:08
 * @description :将dataKeyList转化成输入文件
 */
public class DataKeyListToFile {
	private static String datalist = PropertiesUtil.datalist;

	/**
	 * 面向spark封装，格式为：path \t name \t port
	 * 
	 * @param projectId
	 * @param dataKeyList
	 * @return
	 */
	public static String toSpark(String projectId, List<DataFile> dataList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue()
				+ ".txt";
		FileTools.createFile(dataListFile);
		for (int i = 0; i < dataList.size(); i++) {
			DataFile data = dataList.get(i);
			sb.append(data.getPath()).append("\t").append(data.getFileName()).append("\t").append(SparkPro.START)
					.append("");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 包含路径和文件名，格式为：path \t name
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static String containName(List<DataFile> dataList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue()
				+ ".txt";
		FileTools.createFile(dataListFile);
		for (DataFile data : dataList) {
			sb.append(data.getPath() + "\t" + data.getFileName() + "\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 仅包含路径，格式为：path
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static String onlyPath(List<DataFile> dataList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue()
				+ ".txt";
		FileTools.createFile(dataListFile);
		for (DataFile data : dataList) {
			sb.append(data.getPath() + "\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 面向运行fastq文件的APP封装， 格式分两种：1. path_R1 \t path_R2 2. path
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, String> onlyFastqPath(List<DataFile> dataList) {
		Map<String, String> dataListFileMap = new HashMap<>();
		sortDataList(dataList);
		Iterator<DataFile> chk_it = dataList.iterator();
		StringBuffer sb = null;
		Integer dataReportNum = 0;
		while (chk_it.hasNext()) {
			sb = new StringBuffer();
			String dataListFile = datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue()
					+ ".txt";
			FileTools.createFile(dataListFile);
			DataFile data = chk_it.next();
			String dataKey = data.getDataKey();
			String fname = data.getFileName();
			if (fname.contains("R1") || fname.contains("R2")) {
				int index_r1 = fname.lastIndexOf("R1");
				String commonPrefix = fname.substring(0, index_r1);
				String commonSuffix = fname.substring(index_r1 + 2, fname.length());
				DataFile data_r2 = chk_it.next();
				String fname_r2 = data_r2.getFileName();
				String r2_Suffix = fname_r2.substring(fname_r2.lastIndexOf("R2") + 2, fname_r2.length());
				if (fname_r2.contains(commonPrefix + "R2") && r2_Suffix.equals(commonSuffix)) {
					sb.append(data.getPath()).append("\t").append(data_r2.getPath()).append("\t");
				}
			} else {
				sb.append(data.getPath());
			}
			FileTools.appendWrite(dataListFile, sb.toString());
			dataListFileMap.put(dataKey, dataListFile);
			dataReportNum++;
		}
		dataListFileMap.put("dataReportNum", dataReportNum.toString());
		return dataListFileMap;
	}

	/**
	 * 面向 split 封装，格式为： path_R1 \t path_R2 \t path_lis
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, String> toSplit(List<DataFile> dataList) {
		Map<String, String> dataListFileMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue()
				+ ".txt";
		FileTools.createFile(dataListFile);
		sortDataList(dataList);
		List<String> pathList = new ArrayList<>();
		String endPath = "";
		int i = 0;
		String dataKey = "";
		for (DataFile data : dataList) {
			String path = data.getPath();
			if (path.endsWith(".lis") || path.endsWith(".txt")) {
				endPath = path;
			} else {
				if (i == 0) {
					dataKey = data.getDataKey();
				}
				pathList.add(path);
				i++;
			}
		}
		sb.append(pathList.get(0)).append("\t").append(pathList.get(1)).append("\t").append(endPath);
		FileTools.appendWrite(dataListFile, sb.toString());
		dataListFileMap.put(dataKey, dataListFile);
		return dataListFileMap;
	}

	public static void sortDataList(List<DataFile> dataList) {
		Collections.sort(dataList, new Comparator<DataFile>() {
			@Override
			public int compare(DataFile d1, DataFile d2) {
				return d1.getFileName().compareTo(d2.getFileName());
			}
		});
	}

}
