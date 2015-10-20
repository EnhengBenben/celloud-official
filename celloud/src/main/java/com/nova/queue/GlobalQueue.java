package com.nova.queue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.nova.utils.FileTools;

/**
 * spark命令排队文件
 * 
 * @author lin
 */
public class GlobalQueue {
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
    public synchronized static void poll() {
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new File(filePath).delete();
        FileTools.createFile(filePath);
        for (int i = 1; i < lines.size(); i++) {
            offer(lines.get(i));
        }
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
        return FileTools.countLines(filePath) == 0 ? true : false;
    }
}