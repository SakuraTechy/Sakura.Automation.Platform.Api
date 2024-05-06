package com.sakura.common.utils.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

import com.sakura.common.utils.ssh.FreeSftpUtil;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtil {
    private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);
    
    public static String LINUX_CMD = "/bin/sh";
    public static String WIN_CMD = "cmd.exe";
    // time out
    private static long TIME_OUT = 20;
    // time out unit
    private static TimeUnit TIME_OUT_UNIT = TimeUnit.SECONDS;

    public static void main(String[] args) throws IOException {
        String cmd = "curl -X GET \"http://172.19.5.221:8081/job/Sakura.Web.UI.Automation.Test/buildWithParameters?token=sakura&Name=刘智&Email=liuzhi@sakura.com&Product=AAS_DBSG&Version=V6.1B01-POC&IP=172.19.5.50&Branche=master&Run=172.18.1.118\" --user liuzhi:lz612425";
        Integer result = CommandUtil.runCmd(cmd, 12, TimeUnit.SECONDS);
        log.info(String.valueOf(result));
    }

    public static Integer runCmd(String command) {
        return runCmd(command, TIME_OUT, TIME_OUT_UNIT);
    }

    public static Integer runCmd(String command, long time, TimeUnit unit) {
        int status = 0;
        String[] cmds = createCmd(command);
        String cmd = String.join(" ", cmds);
        log.info("CMD: " + cmd);
        log.info(SystemUtils.LINE_SEPARATOR);
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(cmds);
            status = pro.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("cmd exec error");
        }  finally {
            if (pro != null) {
                log.info(result(pro, time, unit));
                pro.destroy();
            }
        }
        return status;
    }

    public static String result(Process process, long time, TimeUnit unit) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService = new ThreadPoolExecutor
                (1, 30, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30));
        Future<String> result = executorService.submit(new ReaderTask(process.getInputStream()));
        Future<String> errResult = executorService.submit(new ReaderTask(process.getErrorStream()));
        String r1 = resultHandle(result, time, unit);
        String r2 = resultHandle(errResult, time, unit);
        executorService.shutdown();
        if (r2 != null && !r2.isEmpty()) {
            return r2;
        }
        return r1;
    }

    /**
     * 创建cmd
     */
    private static String[] createCmd(String command) {
        if (SystemUtils.IS_OS_LINUX) {
            // linux -c
            return new String[]{LINUX_CMD, "-c", command};
        } else if (SystemUtils.IS_OS_WINDOWS) {
            // win10 /c
            return new String[]{WIN_CMD, "/c", command};
        } else {
            String systemInfo = SystemUtils.OS_NAME + " " + SystemUtils.OS_ARCH + " " + SystemUtils.OS_VERSION;
            throw new RuntimeException("OS Not Supported , the current OS is :" + systemInfo);
        }
    }

    /**
     * 预留显示效果控制，例如日志
     */
    private static String resultHandle(Future<String> result, long time, TimeUnit unit) {
        String tmp = null;
        try {
            tmp = result.get(time, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    private static class ReaderTask implements Callable<String> {
        private InputStream inputStream;
        private StringBuilder queryResult;

        public ReaderTask(InputStream inputStream) {
            this.inputStream = inputStream;
            queryResult = new StringBuilder();
        }

        @Override
        public String call() throws Exception {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bf.readLine()) != null) {
                queryResult.append(line).append(SystemUtils.LINE_SEPARATOR);
            }
            bf.close();
            return queryResult.toString();
        }
    }
}