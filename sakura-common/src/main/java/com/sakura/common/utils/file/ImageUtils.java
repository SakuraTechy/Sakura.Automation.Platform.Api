package com.sakura.common.utils.file;

import com.sakura.common.config.SakuraConfig;
import com.sakura.common.constant.Constants;
import com.sakura.common.utils.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;

/**
 * 图片处理工具类
 *
 * @author liuzhi
 */
public class ImageUtils {
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] getImage(String imagePath) {
        InputStream is = getFile(imagePath);
        try {
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            log.error("图片加载异常 {}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream getFile(String imagePath) {
        try {
            byte[] result = readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        } catch (Exception e) {
            log.error("获取图片异常 {}", e);
        }
        return null;
    }

    /**
     * 读取文件为字节数据
     *
     * @param url 地址
     * @return 字节数据
     */
    public static byte[] readFile(String url) {
        InputStream in = null;
        try {
            if (url.startsWith("http")) {
                // 网络地址
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                in = urlConnection.getInputStream();
            } else {
                // 本机地址
                String localPath = SakuraConfig.getProfile();
                String downloadPath = localPath + StringUtils.substringAfter(url, Constants.RESOURCE_PREFIX);
                in = new FileInputStream(downloadPath);
            }
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("获取文件路径异常 {}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    // 图片转化成base64字符串
    public static String GetImageStr(String imgFile) {
//        String imgFile = "D:\\tanbing.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(System.getProperty("user.dir") + imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
//		BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data).toString();
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static String GenerateImage(String imgStr, String imgFilePath) {
        Base64.Decoder decoder = Base64.getDecoder();
//		BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 去掉base64前缀 data:image/jpeg;base64,
            imgStr = imgStr.substring(imgStr.indexOf(",", 1) + 1, imgStr.length());
            // Base64解码
            byte[] b = decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("", e);
        }
        return imgFilePath;
    }

    /**
     * 图片转base64字符串
     *
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
//		BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data).toString();
    }

    /**
     * base64编码字符串转换为图片
     *
     * @param imgStr base64编码字符串
     * @param path   图片路径
     * @return
     */
    public static boolean base64StrToImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        Base64.Decoder decoder = Base64.getDecoder();
//		BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 去掉base64前缀 data:image/jpeg;base64,
            imgStr = imgStr.substring(imgStr.indexOf(",", 1) + 1, imgStr.length());
            // 解密
            byte[] b = decoder.decode(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 文件夹不存在则自动创建
            File tempFile = new File(System.getProperty("user.dir") + path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 忽略SSL证书验证并设置自定义主机名验证逻辑的临时解决方案（仅限测试环境）
    private static void setupInsecureSSL(String host) {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        HostnameVerifier hv = (hostname, session) -> hostname.equals(host); // 替换为实际的主机名

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String imageUrlToBase64(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        setupInsecureSSL(url.getHost()); // 只有在你需要临时解决这个问题时调用这个方法

        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in)) {

            ByteBuffer bb = ByteBuffer.allocate(1024); // 创建一个缓冲区
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (rbc.read(bb) != -1) { // 读取数据到缓冲区
                bb.flip(); // 切换到读模式
                byte[] buffer = new byte[bb.remaining()];
                bb.get(buffer); // 将缓冲区中的数据复制到临时数组中
                baos.write(buffer, 0, buffer.length); // 将临时数组写入ByteArrayOutputStream

                bb.clear(); // 清空缓冲区为下一次读取做准备
            }

            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
