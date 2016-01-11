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

}
