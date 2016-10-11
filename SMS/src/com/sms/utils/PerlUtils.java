package com.sms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description:perl程序执行方法
 * @author lin
 * @date 2013-7-22 下午4:58:59
 */
public class PerlUtils {

	/**
	 * 序列小工具用的perl程序执行方法
	 * 
	 * @param command
	 *            :要执行的perl程序
	 * @return
	 */
	public static String executeGadgetsPerl(String command) {
		Runtime r = Runtime.getRuntime();
		StringBuffer result = new StringBuffer();
		try {
			Process p = r.exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			String separator = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				result.append(line + separator);
			}
			reader.close();
			p.waitFor();
		} catch (IOException e) {
			System.out.println("命令command=" + command + " 执行失败，错误：" + e);
			return "error";
		} catch (InterruptedException e) {
			System.out.println("命令command=" + command + " 执行失败，错误：" + e);
			return "error";
		}
		return result.toString();
	}
}