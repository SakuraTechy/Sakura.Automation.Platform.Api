package com.sakura.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sakura.common.utils.file.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ddddocrUtil {
    private static final Logger log = LoggerFactory.getLogger(ddddocrUtil.class);
    public static String getCode(String pythonPath, String pythonScript, String captchaUrl, String captchaPath, String captchaSave) {
        String code = "";
        // 判断是否是 HTTPS URL 或 data:image/jpg;base64 格式
        boolean isHttps = captchaUrl.startsWith("https://");
        boolean isDataUri = captchaUrl.startsWith("data:image/jpg;base64,");
        try {
            if (isHttps) {
                captchaUrl = ImageUtils.imageUrlToBase64(captchaUrl);
                captchaPath = ImageUtils.GenerateImage(captchaUrl, captchaPath);
            }else if (isDataUri) {
                captchaPath = ImageUtils.GenerateImage(captchaUrl, captchaPath);
            }
            String[] args = new String[]{pythonPath, pythonScript, captchaPath, captchaSave};
            Process proc = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                code = line.split("=")[0];
            }
            in.close();
            proc.waitFor();
        }catch (IOException | InterruptedException e){
            log.error("",e);
        }
        return code;
    }
    public static void main(String[] args) throws IOException {
        String pythonPath = "D:\\Program\\Python\\3.9.0\\python.exe",
                pythonScript = "D:\\King\\Sakura\\Gitee\\Sakura.Automation.Platform.Api\\ddddocr\\code.py",
                captchaUrl = "D:\\King\\Sakura\\Gitee\\Sakura.Automation.Platform.Api\\ddddocr\\code.png",
                captchaPath = "D:\\King\\Sakura\\Gitee\\Sakura.Automation.Platform.Api\\ddddocr\\code.png",
                captchaSave = "D:\\King\\Sakura\\Gitee\\Sakura.Automation.Platform.Api\\ddddocr\\code.txt";
        log.info(getCode(pythonPath, pythonScript, captchaUrl, captchaPath, captchaSave));
    }
}

