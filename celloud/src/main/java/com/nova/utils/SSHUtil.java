package com.nova.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @Description:java 执行 SSH 工具类
 * @author lin
 * @date 2013-10-28 下午4:17:19
 */
public class SSHUtil {
    Logger log = Logger.getLogger(SSHUtil.class);

    private String host;
    private int port = 22;
    private String userName;
    private String pwd;

    /**
     * @param host
     *            :主机
     * @param userName
     *            :用户名
     * @param pwd
     *            :密码
     */
    public SSHUtil(String host, String userName, String pwd) {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
    }

    /**
     * @param host
     *            ：主机
     * @param port
     *            ：端口
     * @param userName
     *            ：用户名
     * @param pwd
     *            ：密码
     */
    public SSHUtil(String host, int port, String userName, String pwd) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
    }

    /**
     * @param command
     *            ：要执行的命令
     * @param isWait
     *            ：是否等待执行完毕
     * @return
     */
    public boolean sshSubmit(String command, boolean isWait) {
        boolean state = true;
        Connection conn = null;
        Session sess = null;
        try {
            conn = new Connection(host, port);
            conn.connect();
            boolean isLogin = conn.authenticateWithPassword(userName, pwd);
            if (!isLogin) {
                log.error("SSH 连接失败", new IOException("SSH 连接失败"));
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
                state = sess.getExitStatus() == null ? false : sess
                        .getExitStatus() == 0 ? true : false;
                log.info("命令执行" + state);
            } else {
                log.info("命令投递成功");
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