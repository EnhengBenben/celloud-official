package com.sms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @Description:文件操作工具类
 * @author lin
 * @date 2013-7-29 下午7:36:51
 */
public class FileTools {

	/**
	 * 创建文件
	 * 
	 * @param path：
	 *            路径格式若为：/home/down/test.txt，
	 *            若路径不存在，则生成home/down文件夹后生成test.txt文件
	 */
	public static void createFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("文件创建异常：" + e);
		}
	}

	/**
	 * 向文件中追加内容
	 * 
	 * @param filePath
	 *            ：要写入的文件
	 * @param writeContext
	 *            ：要追加的内容
	 * @throws IOException
	 */
	public static void appendWrite(String filePath, String writeContext) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(writeContext);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFileToString(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return readFileToString(file);
	}

	public static String readFileToString(File file) {
		if (!file.exists()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			FileReader fr = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fr);
			String line = null;
			while ((line = lnr.readLine()) != null) {
				sb.append(line);
			}
			lnr.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}