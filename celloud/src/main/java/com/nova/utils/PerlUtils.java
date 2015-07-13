package com.nova.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * @Description:perl程序执行方法
 * @author lin
 * @date 2013-7-22 下午4:58:59
 */
public class PerlUtils {
	private static Logger log = Logger.getLogger(PerlUtils.class);

	/**
	 * 调用本地的perl程序获取执行结果列表--第一步
	 * 
	 * @param path
	 * @return
	 */
	public static String getResult(String path) {
		StringBuffer msg = new StringBuffer();
		String command = "perl /share/biosoft/perl/16s_local/bin/16s_analysis_step1_for250.pl  --input "
				+ path;
		log.info("command=" + command);
		BufferedReader reader = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			InputStream ins = proc.getInputStream();
			reader = new BufferedReader(new InputStreamReader(
					ins));
			String str = "";
			String separator = System.getProperty("line.separator");
			while ((str = reader.readLine()) != null) {
				msg.append(str + separator);
			}
			log.info("msg=" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return msg.toString();
	}

	/**
	 * 调用本地perl程序获取svg图和详细信息列表--第二步
	 * 
	 * @param path
	 * @param fileName
	 * @param name
	 * @return
	 */
	public static String getSvgAndData(String path, String fileName, String name) {
		String msg = "";
		String command = "perl /share/biosoft/perl/16s_local/bin/16s_analysis_step2_for250.pl  --input "
				+ path + " --blast " + path + ".fa.blast.out --name " + name;
		log.info("command=" + command);
		BufferedReader reader = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			InputStream ins = proc.getInputStream();
			reader = new BufferedReader(new InputStreamReader(
					ins));
			String str = "";
			String separator = System.getProperty("line.separator");
			while ((str = reader.readLine()) != null) {
				msg += str + separator;
			}
			log.info("msg=" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return msg;
	}

	public static String getResult() {
		String str = "";
		try {
			str = FileUtils.readFileToString(new File(ServletActionContext
					.getServletContext().getRealPath("/file") + "/test1.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getSvgAndData() {
		String str = "";
		try {
			str = FileUtils.readFileToString(new File(ServletActionContext
					.getServletContext().getRealPath("/file") + "/test2.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 序列小工具用的perl程序执行方法
	 * 
	 * @param command
	 *            :要执行的perl程序
	 * @return
	 */
	public static String executeGadgetsPerl(String command) {
		log.info("执行命令：command=" + command);
		Runtime r = Runtime.getRuntime();
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		try {
			Process p = r.exec(command);
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			String separator = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				result.append(line + separator);
			}
			reader.close();
			p.waitFor();
		} catch (IOException e) {
			log.error("命令command=" + command + " 执行失败，错误：" + e);
			return "error";
		} catch (InterruptedException e) {
			log.error("命令command=" + command + " 执行失败，错误：" + e);
			return "error";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}

	public static String excutePerl(String command) {
		log.info("执行命令：command=" + command);
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc =runtime.exec(command);
			InputStream ins = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			while (reader.readLine() != null) {
			}
		} catch (IOException e) {
			log.error("命令command=" + command + " 执行失败，错误：" + e);
			return "error";
		}
		return "success";
	}
}
