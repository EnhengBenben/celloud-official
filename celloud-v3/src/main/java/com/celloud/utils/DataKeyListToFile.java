package com.celloud.utils;

import java.util.Date;
import java.util.List;

import com.celloud.constants.PortPool;

/**
 * 
 * @author lin
 * @date 2015-12-10 下午2:53:08
 * @description :将dataKeyList转化成输入文件
 */
public class DataKeyListToFile {
    private static String dataPath = PropertiesUtil.bigFilePath;
    private static String datalist = PropertiesUtil.datalist;

    /**
     * 面向spark封装，格式为：path \t name \t port
     * 
     * @param projectId
     * @param dataKeyList
     * @return
     */
    public static String toSpark(String projectId, String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        List<String> ports = PortPool.getPorts(dataArray.length, projectId);
        for (int i = 0; i < dataArray.length; i++) {
            String[] dataDetail = dataArray[i].split(",");
            sb.append(dataPath + FileTools.getArray(dataDetail, 1) + "\t"
                    + FileTools.getArray(dataDetail, 2) + "\t" + ports.get(i)
                    + "\n");
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
    public static String containName(String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        for (int i = 0; i < dataArray.length; i++) {
            String[] dataDetail = dataArray[i].split(",");
            sb.append(dataPath + FileTools.getArray(dataDetail, 1) + "\t"
                    + FileTools.getArray(dataDetail, 2) + "\n");
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
    public static String onlyPath(String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        for (int i = 0; i < dataArray.length; i++) {
            String[] detail = dataArray[i].split(",");
            sb.append(dataPath + detail[1] + "\n");
        }
        FileTools.appendWrite(dataListFile, sb.toString());
        return dataListFile;
    }

    /**
     * 面向 CMP 封装，格式为：
     * 
     * @param dataKeyList
     * @return
     */
    public static String toCMP(String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        for (int i = 0; i < dataArray.length; i = i + 2) {
            String[] detail1 = dataArray[i].split(",");
            String[] detail2 = dataArray[i + 1].split(",");
            sb.append(dataPath + detail1[1] + "\t" + dataPath + detail2[1]
                    + "\n");
        }
        FileTools.appendWrite(dataListFile, sb.toString());
        return dataListFile;
    }

    /**
     * 面向 split 封装，格式为：
     * 
     * @param dataKeyList
     * @return
     */
    public static String toSplit(String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        for (int i = 0; i < dataArray.length; i = i + 3) {
            String detail1 = dataArray[i].split(",")[1];
            String detail2 = dataArray[i + 1].split(",")[1];
            String detail3 = dataArray[i + 2].split(",")[1];
            String endData = "";
            String d1 = "";
            String d2 = "";
            if (FileTools.getExt(detail1).equals(".lis")) {
                endData = detail1;
                d1 = detail2;
                d2 = detail3;
            } else if (FileTools.getExt(detail2).equals(".lis")) {
                endData = detail2;
                d1 = detail1;
                d2 = detail3;
            } else if (FileTools.getExt(detail3).equals(".lis")) {
                endData = detail3;
                d1 = detail1;
                d2 = detail2;
            }
            sb.append(dataPath).append(d1).append("\t").append(dataPath)
                    .append(d2).append("\t").append(dataPath).append(endData)
                    .append("\n");
        }
        FileTools.appendWrite(dataListFile, sb.toString());
        return dataListFile;
    }

}
