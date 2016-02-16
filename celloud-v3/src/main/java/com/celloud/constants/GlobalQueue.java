package com.celloud.constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.celloud.utils.FileTools;

/**
 * spark命令排队文件
 * 
 * @author lin
 */
public class GlobalQueue {
	private static Logger log = Logger.getLogger(GlobalQueue.class);
	/**
	 * 定义排队文件路径
	 */
	private static String filePath = "/share/data/command/command.txt";

	/**
	 * 将命令追加到文件尾部
	 * 
	 * @param e
	 */
	public synchronized static void offer(String e) {
		FileTools.appendWrite(filePath, e + "\n");
	}

	/**
	 * 移除排队文件的第一个命令
	 * 
	 * @return
	 */
	public synchronized static boolean poll() {
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			log.error("读取spark排队文件失败");
		}
		if (lines == null) {
			return false;
		}
		new File(filePath).delete();
		FileTools.createFile(filePath);
		if (lines.size() > 1) {
			for (int i = 1; i < lines.size(); i++) {
				offer(lines.get(i));
			}
		}
		return true;
	}

	/**
	 * 获取排队文件的第一个命令；如果此文件为空，则返回 null。
	 * 
	 * @return
	 */
	public synchronized static String peek() {
		return FileTools.getFirstLine(filePath);
	}

	/**
	 * 判断排队文件是否为空
	 * 
	 * @return
	 */
	public synchronized static boolean isEmpty() {
		return FileTools.countLines(filePath) == 0;
	}
}