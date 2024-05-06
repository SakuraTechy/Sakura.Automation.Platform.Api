package com.sakura.common.utils.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuzhi
 * @date 2020年10月16日 下午4:44:10
 */
@SuppressWarnings({"unused"})
public class FreeSshUtil {

    private static final Logger log = LoggerFactory.getLogger(FreeSshUtil.class);

    static String ip;
    static int port;
    static String username;
    static String password;

    public FreeSshUtil() {
//        ip = ConfigUtil.getProperty("MI_8_FreeSSHd_IP", Constants.CONFIG_APP);
//        port = Integer.parseInt(ConfigUtil.getProperty("MI_8_FreeSSHd_Port", Constants.CONFIG_APP));
//        username = ConfigUtil.getProperty("MI_8_FreeSSHd_UserName", Constants.CONFIG_APP);
//        password = ConfigUtil.getProperty("MI_8_FreeSSHd_PassWord", Constants.CONFIG_APP);
    }

    public static void cmd(String cmd) {
        try {
            Connection conn = new Connection(ip);
            conn.connect();
            log.info("开始Linux连接Windows：" + ip + " " + username + " " + password);
            log.info("ssh " + username + "@" + ip);
            conn.authenticateWithPassword(username, password);
            Session sess = conn.openSession();
            log.info("连接成功，开始执行cmd命令");
            log.info("cmd /c " + cmd);
            sess.execCommand("cmd /c " + cmd);
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "utf-8"));
            // while (true) {
            // String line = br.readLine();
            // if (line == null)
            // break;
            // log.info(line);
            // }
            sess.close();
            conn.close();
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("", e);
        }
    }

    public static void cmd(String ip, String username, String password, String cmd) {
        try {
            // 建立连接
            Connection conn = new Connection(ip);
            conn.connect();
            // 利用用户名和密码进行授权
            log.info("开始Linux连接Windows：" + ip + " " + username + " " + password);
            log.info("ssh " + username + "@" + ip);
            conn.authenticateWithPassword(username, password);
            // 打开会话
            Session sess = conn.openSession();
            log.info("连接成功，开始执行cmd命令");
            // 执行命令
            log.info("cmd /c " + cmd);
            sess.execCommand("cmd /c " + cmd);
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "utf-8"));
//             while (true) {
//             String line = br.readLine();
//             if (line == null)
//             break;
//             log.info(line);
//             }
            sess.close();
            conn.close();
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("", e);
        }
    }

    public static void main(String[] args) {
        cmd("172.18.1.118", "king", "111111", "git clone http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git");
//        // cmd("10.18.22.65", "Administrator", "111111", "cd c: &&rd 123.txt");
    }
}
