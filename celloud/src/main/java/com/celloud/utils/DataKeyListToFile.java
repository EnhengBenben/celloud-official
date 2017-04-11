package com.celloud.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.model.mysql.DataFile;

/**
 * 
 * @author lin
 * @date 2015-12-10 下午2:53:08
 * @description :将dataKeyList转化成输入文件
 */
public class DataKeyListToFile {
	private static Logger logger = LoggerFactory.getLogger(DataKeyListToFile.class);
	private static String datalist = PropertiesUtil.datalist;
	public static final String DATA_REPORT_NUM = "dataReportNum";

	/**
	 * @Description 包含路径和文件名，格式为：path \t name
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2017年2月28日 下午4:26:17
	 */
	public static Map<String, Object> projectContainName(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		Iterator<DataFile> iterator = dataList.iterator();
		DataFile first = dataList.get(0);
		String dataListFile = getDataListFile(first.getOssPath() != null);
		while (iterator.hasNext()) {
			DataFile data = iterator.next();
			FileTools.appendWrite(dataListFile, path(data) + "\t" + data.getFileName() + "\n");
		}
		dataListFileMap.put(first.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
		dataListFileMap.put(DATA_REPORT_NUM, String.valueOf(dataList.size()));
		return dataListFileMap;
	}

	/**
	 * 包含路径和文件名，格式为：path \t name
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, Object> containName(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile data = iterator.next();
			String dataListFile = getDataListFile(data.getOssPath() != null);
			FileTools.appendWrite(dataListFile, path(data) + "\t" + data.getFileName());
			dataListFileMap.put(data.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
		}
		dataListFileMap.put(DATA_REPORT_NUM, String.valueOf(dataList.size()));
		return dataListFileMap;
	}

	/**
	 * 仅包含路径，格式为：path
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, Object> onlyPath(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile data = iterator.next();
			String dataListFile = getDataListFile(false);
			FileTools.appendWrite(dataListFile, data.getPath());
			dataListFileMap.put(data.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
		}
		dataListFileMap.put(DATA_REPORT_NUM, String.valueOf(dataList.size()));
		return dataListFileMap;
	}

	/**
	 * 两对fastq文件分别运行，但是4个数据缺一不可<br>
	 * 1. ...A_R1.fastq <br>
	 * 2. ...A_R2.fastq <br>
	 * 1&2运行一次<br>
	 * 3. ...B_R1.fastq<br>
	 * 4. ...B_R2.fastq<br>
	 * 3&4运行一次
	 * 
	 * @param dataList
	 * @return
	 * @author leamo
	 * @date 2016年11月8日 下午2:16:01
	 */
	public static Map<String, Object> abFastqPath(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		sortDataList(dataList);
		List<DataFile> canRunDataList = new ArrayList<>();
		Iterator<DataFile> chk_it = dataList.iterator();
		StringBuffer dataFileInfo = null;
		Integer dataReportNum = 0;
		Integer listIndex = 0;
		while (chk_it.hasNext()) {
			DataFile data_AR1 = chk_it.next();
			listIndex++;

			String fname_AR1 = data_AR1.getFileName();
			// 从A_R1数据开始向下查找数据
			if (fname_AR1.contains("A_R1")) {
				int index_AR1 = fname_AR1.lastIndexOf("A_R1");
				String commonPrefix = fname_AR1.substring(0, index_AR1);
				// 满足条件：1. A_R1数据之后还有至少3个数据
				// 2. 向下第二个是以 “ A数据的公共部分+A_R2”开始
				// 3. 向下第三个是以 “ A数据的公共部分+B_R1”开始
				// 4. 向下第四个是以 “ A数据的公共部分+B_R2”开始
				if (dataList.size() >= (listIndex + 3)
						&& dataList.get(listIndex).getFileName().startsWith(commonPrefix + "A_R2")
						&& dataList.get(listIndex + 1).getFileName().startsWith(commonPrefix + "B_R1")
						&& dataList.get(listIndex + 2).getFileName().startsWith(commonPrefix + "B_R2")) {
					DataFile data_AR2 = chk_it.next();
					DataFile data_BR1 = chk_it.next();
					DataFile data_BR2 = chk_it.next();
					listIndex += 3;

					dataFileInfo = new StringBuffer();
					dataFileInfo.append(data_AR1.getOssPath() == null ? data_AR1.getPath() : data_AR1.getOssPath())
							.append("\t")
							.append(data_AR2.getOssPath() == null ? data_AR2.getPath() : data_AR2.getOssPath())
							.append("\t")
							.append(data_BR1.getOssPath() == null ? data_BR1.getPath() : data_BR1.getOssPath())
							.append("\t")
							.append(data_BR2.getOssPath() == null ? data_BR2.getPath() : data_BR2.getOssPath())
							.append("\t");
					String dataListFile = getDataListFile(data_AR1.getOssPath() != null);
					FileTools.appendWrite(dataListFile, dataFileInfo.toString());
					dataListFileMap.put(data_AR1.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
					dataReportNum++;
					canRunDataList.add(data_AR1);
					canRunDataList.add(data_AR2);
					canRunDataList.add(data_BR1);
					canRunDataList.add(data_BR2);
				}
			}
		}
		dataListFileMap.put(DATA_REPORT_NUM, dataReportNum.toString());
		dataListFileMap.put("canRunDataList", canRunDataList);
		return dataListFileMap;
	}

	/**
	 * 面向运行fastq文件的APP封装， 格式分两种：1. path_R1 \t path_R2 2. path
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, Object> onlyFastqPath(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		List<DataFile> canRunDataList = new ArrayList<>();
		sortDataList(dataList);
		Iterator<DataFile> chk_it = dataList.iterator();
		StringBuffer dataFileInfo = null;
		Integer dataReportNum = 0;
		Integer listIndex = 0;
		while (chk_it.hasNext()) {
			DataFile data_R1 = chk_it.next();
			listIndex++;

			String fname_R1 = data_R1.getFileName();
			// 从A_R1数据开始向下查找数据
			if (fname_R1.contains("R1")) {
				int index_R1 = fname_R1.lastIndexOf("R1");
				String commonPrefix = fname_R1.substring(0, index_R1);
				// 满足条件：1. R1数据之后还有至少1个数据
				// 2. 向下第二个是以 “ 数据的公共部分+R2”开始
				if (dataList.size() >= (listIndex + 1)
						&& dataList.get(listIndex).getFileName().startsWith(commonPrefix + "R2")) {
					DataFile data_R2 = chk_it.next();
					listIndex++;

					dataFileInfo = new StringBuffer();
                    dataFileInfo.append(data_R1.getOssPath() == null ? data_R1.getPath() : data_R1.getOssPath())
							.append("\t")
							.append(data_R2.getOssPath() == null ? data_R2.getPath() : data_R2.getOssPath())
							.append("\t");
					String dataListFile = getDataListFile(data_R1.getOssPath() != null);
					FileTools.appendWrite(dataListFile, dataFileInfo.toString());
					dataListFileMap.put(data_R1.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
					dataReportNum++;
					canRunDataList.add(data_R1);
					canRunDataList.add(data_R2);
				}
            } else if (fname_R1.contains(".tar.gz") && !fname_R1.contains("R2")) {
				dataFileInfo = new StringBuffer();
                dataFileInfo.append(data_R1.getPath())
						.append("\t");
                String dataListFile = getDataListFile(false);
				FileTools.appendWrite(dataListFile, dataFileInfo.toString());
				dataListFileMap.put(data_R1.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
				dataReportNum++;
				canRunDataList.add(data_R1);
			}
		}
		dataListFileMap.put(DATA_REPORT_NUM, dataReportNum.toString());
		dataListFileMap.put("canRunDataList", canRunDataList);
		return dataListFileMap;
	}

	/**
	 * @Description:根据parameters配对dataList
	 * @param dataList
	 *            只支持长度为2的倍数
	 * @param parameters
	 *            只支持长度为2
	 * @return
	 * @author lin
	 * @date 2017年2月9日 下午2:41:59
	 */
	public static Map<String, Object> pair(List<DataFile> dataList, String... parameters) {
		// XXX 希望可以匹配任意多的配对条件
		// 1.dataList长度需要是parameters长度的整数倍
		// 2.parameters的规则要和APPID绑定，不能这样传入
		// 3.while内部也许需要递归
		if (dataList == null || parameters == null)
			return null;
		if (dataList.size() % 2 != 0) {
			logger.error("运行数据有误！");
			return null;
		}
		Integer pairNum = parameters.length;
		if (pairNum != 2) {
			logger.error("配对条件有误！");
			return null;
		}
		Map<String, Object> dataListFileMap = new HashMap<>();
		List<DataFile> canRunDataList = new ArrayList<>();
		// 均排序
		sortDataList(dataList);
		Arrays.sort(parameters);

		Iterator<DataFile> chk_it = dataList.iterator();
		Integer dataReportNum = 0;
		Integer listIndex = 0;
		while (chk_it.hasNext()) {
			listIndex++;
			DataFile data_left = chk_it.next();
			String fname_left = data_left.getFileName();
			if (!fname_left.contains(parameters[0])) {
				logger.error("文件名称不符合配对条件" + fname_left);
				continue;
			}
			int index = fname_left.lastIndexOf(parameters[0]);
			String commonPrefix = fname_left.substring(0, index);
			// 满足条件：1. 后面还有至少1个数据
			// 2. 后面的数据是以 “ 数据的公共部分+匹配条件2” 开始
			if (dataList.size() >= (listIndex + 1)
					&& dataList.get(listIndex).getFileName().startsWith(commonPrefix + parameters[1])) {
				DataFile data_right = chk_it.next();
				listIndex++;

				StringBuffer dataFileInfo = new StringBuffer();
				dataFileInfo.append(data_left.getOssPath() == null ? data_left.getPath() : data_left.getOssPath())
						.append("\t")
						.append(data_right.getOssPath() == null ? data_right.getPath() : data_right.getOssPath());
				String dataListFile = getDataListFile(data_left.getOssPath() != null);
				FileTools.appendWrite(dataListFile, dataFileInfo.toString());
				dataListFileMap.put(data_left.getDataKey(), UploadPathUtils.getObjectKeyByPath(dataListFile));
				dataReportNum++;
				canRunDataList.add(data_left);
				canRunDataList.add(data_right);
			}

		}
		dataListFileMap.put(DATA_REPORT_NUM, dataReportNum.toString());
		dataListFileMap.put("canRunDataList", canRunDataList);
		return dataListFileMap;
	}

	/**
	 * 面向 split 封装，格式为： path_R1 \t path_R2 \t path_lis
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, Object> toSplit(List<DataFile> dataList) {
		Map<String, Object> dataListFileMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sortDataList(dataList);
		List<String> pathList = new ArrayList<>();
		String endPath = "";
		int i = 0;
		String dataKey = "";
		boolean inOSS = false;
		for (DataFile data : dataList) {
			String filename = data.getFileName();
			if (filename.endsWith(".lis") || filename.endsWith(".txt")) {
				endPath = data.getPath();
			} else {
				if (i == 0) {
					dataKey = data.getDataKey();
				}
				pathList.add(data.getPath());
				i++;
			}
		}
		String dataListFile = getDataListFile(inOSS);
		sb.append(pathList.get(0)).append("\t").append(pathList.get(1)).append("\t").append(endPath);
		FileTools.appendWrite(dataListFile, sb.toString());
		dataListFileMap.put(dataKey, UploadPathUtils.getObjectKeyByPath(dataListFile));
		dataListFileMap.put(DATA_REPORT_NUM, "1");
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

	private static String getDataListFile(boolean inOSS) {
		return (inOSS ? UploadPathUtils.getListPathInOSS() : datalist) + new Date().getTime() + "_"
				+ new Double(Math.random() * 1000).intValue() + ".txt";
	}

	private static String path(DataFile data) {
		return data.getOssPath() == null ? data.getPath() : data.getOssPath();
	}
}
