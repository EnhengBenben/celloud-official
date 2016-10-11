package com.sms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

}