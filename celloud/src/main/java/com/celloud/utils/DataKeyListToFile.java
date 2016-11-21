package com.celloud.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.DataFile;

/**
 * 
 * @author lin
 * @date 2015-12-10 下午2:53:08
 * @description :将dataKeyList转化成输入文件
 */
public class DataKeyListToFile {
	private static String datalist = PropertiesUtil.datalist;
	public static final String DATA_REPORT_NUM = "dataReportNum";

	/**
	 * 包含路径和文件名，格式为：path \t name
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static Map<String, String> containName(List<DataFile> dataList) {
		Map<String, String> dataListFileMap = new HashMap<>();
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile data = iterator.next();
			String dataListFile = getDataListFile();
			FileTools.appendWrite(dataListFile, data.getPath() + "\t" + data.getFileName());
			dataListFileMap.put(data.getDataKey(), dataListFile);
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
	public static Map<String, String> onlyPath(List<DataFile> dataList) {
		Map<String, String> dataListFileMap = new HashMap<>();
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile data = iterator.next();
			String dataListFile = getDataListFile();
			FileTools.appendWrite(dataListFile, data.getPath());
			dataListFileMap.put(data.getDataKey(), dataListFile);
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
    public static Map<String, String> abFastqPath(List<DataFile> dataList) {
        Map<String, String> dataListFileMap = new HashMap<>();
        sortDataList(dataList);
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
                        && dataList.get(listIndex).getFileName()
                                .startsWith(commonPrefix + "A_R2")
                        && dataList.get(listIndex + 1).getFileName()
                                .startsWith(commonPrefix + "B_R1")
                        && dataList.get(listIndex + 2).getFileName()
                                .startsWith(commonPrefix + "B_R2")) {
                    DataFile data_AR2 = chk_it.next();
                    DataFile data_BR1 = chk_it.next();
                    DataFile data_BR2 = chk_it.next();
                    listIndex += 3;

                    dataFileInfo = new StringBuffer();
                    dataFileInfo.append(data_AR1.getPath()).append("\t")
                            .append(data_AR2.getPath()).append("\t")
                            .append(data_BR1.getPath()).append("\t")
                            .append(data_BR2.getPath()).append("\t");
                    String dataListFile = getDataListFile();
                    FileTools.appendWrite(dataListFile,
                            dataFileInfo.toString());
                    dataListFileMap.put(data_AR1.getDataKey(), dataListFile);
                    dataReportNum++;
                }
            }
        }
        dataListFileMap.put(DATA_REPORT_NUM, dataReportNum.toString());
        return dataListFileMap;
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
            String dataListFile = getDataListFile();
            DataFile data = chk_it.next();
            String dataKey = data.getDataKey();
            String fname = data.getFileName();
            if (fname.contains("R1") || fname.contains("R2")) {
                int index_r1 = fname.lastIndexOf("R1");
                String commonPrefix = fname.substring(0, index_r1);
                String commonSuffix = fname.substring(index_r1 + 2,
                        fname.length());
                DataFile data_r2 = chk_it.next();
                String fname_r2 = data_r2.getFileName();
                String r2_Suffix = fname_r2.substring(
                        fname_r2.lastIndexOf("R2") + 2, fname_r2.length());
                if (fname_r2.contains(commonPrefix + "R2")
                        && r2_Suffix.equals(commonSuffix)) {
                    sb.append(data.getPath()).append("\t")
                            .append(data_r2.getPath()).append("\t");
                }
            } else {
                sb.append(data.getPath());
            }
            FileTools.appendWrite(dataListFile, sb.toString());
            dataListFileMap.put(dataKey, dataListFile);
            dataReportNum++;
        }
        dataListFileMap.put(DATA_REPORT_NUM, dataReportNum.toString());
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
		String dataListFile = getDataListFile();
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

	private static String getDataListFile() {
		return datalist + new Date().getTime() + "_" + new Double(Math.random() * 1000).intValue() + ".txt";
	}

}
