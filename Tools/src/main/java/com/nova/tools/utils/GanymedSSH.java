package com.nova.tools.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @Description:java 操作 linux 工具类
 * @author lin
 * @date 2013-10-28 下午4:17:19
 */
public class GanymedSSH {
    Logger log = Logger.getLogger(GanymedSSH.class);

    private String host;
    private String userName;
    private String pwd;
    private String command;

    /**
     * @param host
     *            :计算节点
     * @param userName
     *            :用户名
     * @param pwd
     *            :密码
     * @param command
     *            :命令行
     */
    public GanymedSSH(String host, String userName, String pwd, String command) {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
        this.command = command;
    }

    /**
     * ssh方式提交命令
     * 
     * @return
     */
    public boolean sshSubmit(boolean isWait) {
        boolean state = true;
        Connection conn = null;
        Session sess = null;
        try {
            conn = new Connection(host);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(userName,
                    pwd);
            if (isAuthenticated == false) {
                log.error("连接 SGE 集群失败", new IOException("连接 SGE 集群失败"));
                return false;
            }
            // 开启 session ，执行命令
            sess = conn.openSession();
            sess.execCommand(command);
            log.info("开始执行：" + command);
            if (isWait) {
                // 循环结果
                InputStream stdout = new StreamGobbler(sess.getStdout());
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        stdout));
                String line = "";
                while (line != null) {
                    line = br.readLine();
                    log.info(line);
                }
                // 获取命令执行结果
                state = sess.getExitStatus() == 0 ? true : false;
                log.info("命令执行" + state);
            } else {
                log.info("命令投递成功，断开SSH连接");
            }
        } catch (IOException e) {
            log.error("命令执行失败", new IOException(e));
        } finally {
            if (sess != null)
                sess.close();
            if (conn != null)
                conn.close();
        }
        return state;
    }
}