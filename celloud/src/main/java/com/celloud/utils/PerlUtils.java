package com.celloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @Description:perl程序执行方法
 * @author lin
 * @date 2013-7-22 下午4:58:59
 */
public class PerlUtils{
    private static Logger log = Logger.getLogger(PerlUtils.class);
    public static String excutePerl(String command) {
        log.info("执行命令：command=" + command);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(command);
            InputStream ins = proc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            while (reader.readLine() != null) {
            }
        } catch (IOException e) {
            log.error("命令command=" + command + " 执行失败，错误：" + e);
            return "error";
        }
        return "success";
    }

	public static void excutePerlNoResult(String command) {
		log.info("执行命令：command=" + command);
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(command);
			// InputStream ins = proc.getInputStream();
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(ins));
			// while (reader.readLine() != null) {
			// }
		} catch (IOException e) {
			log.error("命令command=" + command + " 执行失败，错误：" + e);
		}
	}

    public static boolean excuteCopyFile(String inPath, String outPath) {
        boolean state = false;
        String command = "cp -i " + inPath + " " + outPath;
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec(command);
            log.info("执行命令：command=" + command);
            InputStream ins = proc.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(ins));
            String line = null;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            state = true;
            log.info("拷贝命令执行" + state);
        } catch (IOException e) {
            log.error("命令command=" + command + " 执行失败，错误：" + e);
            e.printStackTrace();
        }
        return state;
    }
}
