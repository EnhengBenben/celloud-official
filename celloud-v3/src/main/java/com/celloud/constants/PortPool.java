package com.celloud.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.celloud.constants.SparkPro;
import com.celloud.utils.FileTools;

/**
 * 端口池
 * 
 * @author lin
 */
public class PortPool {
	private static Logger log = Logger.getLogger(PortPool.class);
	/**
	 * 记录端口的文件
	 */
	private static String path = "/share/data/command/ports.txt";

	/**
	 * 记录项目和端口关系的文件
	 */
	private static String pro_ports = "/share/data/command/pro_ports.txt";

	private static boolean isInit = false;

	/**
	 * 初始化端口文件
	 * 
	 * @author lin
	 * @date 2016年1月19日下午1:39:05
	 */
	public static void init() {
		File f = new File(path);
		if (!f.exists()) {
			FileTools.createFile(path);
			// 锁定端口文件
			FileLock lock = FileTools.getFileLock(f);
			StringBuffer sb = new StringBuffer();
			for (int i = SparkPro.START; i < SparkPro.START + SparkPro.NODES; i++) {
				sb.append(i + "\n");
			}
			FileTools.appendWrite(path, sb.toString());
			// 释放锁
			try {
				lock.release();
			} catch (IOException e) {
				log.error("释放锁异常:" + e);
			}
		}
		isInit = true;
	}

	/**
	 * 获取端口
	 * 
	 * @param need
	 *            ：需要的端口数量
	 * @param projectId
	 *            ：项目ID
	 * @return：可用的端口List<String>，若返回null则端口不足
	 */
	public synchronized static List<String> getPorts(int need, String projectId) {
		if (!isInit) {
			init();
		}
		// 需要的端口比可提供的多，则返回null
		if (need > getSize()) {
			return null;
		}
		// 锁定端口文件
		File f = new File(path);
		FileLock lock = FileTools.getFileLock(f);
		// 锁定项目端口关系文件
		File proports = new File(pro_ports);
		FileLock pro_ports_lock = FileTools.getFileLock(proports);
		// 记录需要返回的端口
		List<String> ports = new ArrayList<>();
		// 记录需要回写的端口
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(f);
		} catch (IOException e) {
			log.error(f.getAbsoluteFile() + "读取文件异常");
		}
		new File(path).delete();
		FileTools.createFile(path);
		// 记录返回的端口
		for (int i = 0; i < need; i++) {
			ports.add(lines.get(i));
		}
		// 增加项目端口关系
		FileTools.appendWrite(pro_ports, projectId + "\t" + ports.toString() + "\n");
		// 回写剩余的端口
		if (lines != null) {
			for (int i = need; i < lines.size(); i++) {
				FileTools.appendWrite(path, lines.get(i) + "\n");
			}
		}
		// 释放锁
		try {
			pro_ports_lock.release();
			lock.release();
		} catch (IOException e) {
			log.error("释放锁异常:" + e);
		}
		return ports;
	}

	/**
	 * 将 projectId 占用的端口放回端口池
	 * 
	 * @param projectId
	 */
	public synchronized static void setPort(String projectId) {
		// 锁定关系文件
		File f = new File(pro_ports);
		FileLock lock = FileTools.getFileLock(f);
		// 锁定端口文件
		FileLock portsLock = FileTools.getFileLock(new File(path));
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			log.error(pro_ports + "文件不存在");
		}
		String line = null;
		// 记录需要回写的项目端口关系
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = br.readLine()) != null) {
				if (line.split("\t")[0].equals(projectId)) {
					String ps = line.split("\t")[1];
					String[] ports = ps.substring(1, ps.length() - 1).split(",");
					for (String port : ports) {
						// 回写端口
						FileTools.appendWrite(path, port.trim() + "\n");
					}
				} else {
					sb.append(line).append("\n");
				}
			}
		} catch (IOException e) {
			log.error("IOException：" + e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				log.error("关闭流异常：" + e);
			}
		}
		f.delete();
		FileTools.createFile(pro_ports);
		FileTools.appendWrite(pro_ports, sb.toString());

		try {
			portsLock.release();
			lock.release();
		} catch (IOException e) {
			log.error("释放锁异常:" + e);
		}
	}

	/**
	 * 端口池是否为空
	 * 
	 * @return
	 */
	public synchronized static boolean isEmpyt() {
		return getSize() == 0;
	}

	/**
	 * 获取可用端口数量（也就等于集群可用节点数量）
	 * 
	 * @return
	 */
	public synchronized static int getSize() {
		if (!isInit) {
			init();
		}
		File f = new File(path);
		// 获取锁
		FileLock lock = FileTools.getFileLock(f);
		// 统计端口数量
		int line = FileTools.countLines(f);
		try {
			// 释放锁
			lock.release();
		} catch (IOException e) {
			log.error("释放锁异常:" + e);
		}
		return line;
	}

}
