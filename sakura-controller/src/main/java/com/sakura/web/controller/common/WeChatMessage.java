package com.sakura.web.controller.common;

import lombok.Data;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class WeChatMessage {
    private String url;
    private String msgtype;
    private Markdown markdown;

    private List<Markdown> markdownList;
    @Data
    public static class Markdown {
        private String orderId;
        private String userName;
        private String productChName;
        private String productVersionNumber;
        private String typeName;
        private String machineCodeMd;
        private String uploadFileName;

        private String makeUserName;
        private String certificateState;
        private String makeTime;
        private String authorizationDeadlineTime;
        private String maintenanceWarnDate;
        private String fileName;
    }

    public static int sendMessage1(WeChatMessage message) {
        int responseCode = 0;
        try {
            // 创建URL对象
            URL url = new URL(message.getUrl());
            // 开启HTTP连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // 构建消息体

//            String content = message.getMarkdown().getContent();
//            System.out.println("Sending message: " + content);
//            content = Pattern.compile("&gt;").matcher(content).replaceAll(">");
            String content = "产品证书一键自动化制作成功，<font color=\\\"warning\\\">共1个</font>，详情如下，请相关同事注意。 \n" +
                    ">申请姓名：<font color=\\\"info\\\">"+ message.getMarkdown().getUserName() + "</font>\n " +
                    ">产品名称：<font color=\\\"comment\\\">"+ message.getMarkdown().getProductChName() + "</font>\n" +
                    ">产品版本：<font color=\\\"comment\\\">" + message.getMarkdown().getProductVersionNumber() + "</font>\n " +
                    ">产品型号：<font color=\\\"comment\\\">" + message.getMarkdown().getTypeName() + "</font>\n" +
                    ">证书编码：<font color=\\\"comment\\\">" + message.getMarkdown().getMachineCodeMd() + "</font>\n" +
                    ">机器码名：<font color=\\\"comment\\\">" + message.getMarkdown().getUploadFileName() + "</font>\n" +
                    ">产品证书：[点击下载](" + message.getMarkdown().getFileName() + ")\n" +
                    ">制作时间：<font color=\\\"info\\\">" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</font>";
            String textTemplate = "{\n" +
                    "    \"msgtype\": \""+ message.getMsgtype()+"\",\n" +
                    "    \"markdown\": {\n" +
                    "        \"content\": \"" + content+ "\",\n" +
                    "    }\n" +
                    "}";
            // 发送请求
            // 使用 ObjectMapper 将 Java 对象转换为 JSON 字符串
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonMessage = objectMapper.writeValueAsString(textTemplate);
            System.out.println("Sending message: " + textTemplate);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(textTemplate.getBytes(StandardCharsets.UTF_8));
            }
            // 获取响应码
            responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    public static int sendMessage(WeChatMessage message) {
        int responseCode = 0;
        try {
            // 创建URL对象
            URL url = new URL(message.getUrl());
            // 开启HTTP连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // 构建消息体
            StringBuilder content = new StringBuilder();
            List<WeChatMessage.Markdown> markdownList = message.getMarkdownList();
            content.append("一键自动化制作产品证书成功，<font color=\\\"warning\\\">共").append(markdownList.size()).append("个</font>，详情如下，请相关同事注意。\n");
            for (WeChatMessage.Markdown markdown : markdownList) {
                if(markdownList.size() > 1){
                    content.append("-------------------------------------------------------------------\n");
                }
                appendDynamicContentBlock(content, markdown);
            }
            String textTemplate = "{\n" +
                    "    \"msgtype\": \""+ message.getMsgtype()+"\",\n" +
                    "    \"markdown\": {\n" +
                    "        \"content\": \"" + content+ "\",\n" +
                    "    }\n" +
                    "}";
            // 发送请求
            System.out.println("Sending message: " + textTemplate);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(textTemplate.getBytes(StandardCharsets.UTF_8));
            }
            // 获取响应码
            responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    private static void appendDynamicContentBlock(StringBuilder content, WeChatMessage.Markdown markdown) {
        content.append(">申请编号：<font color=\\\"info\\\">").append(markdown.getOrderId()).append("</font>\n");
        content.append(">申请姓名：<font color=\\\"comment\\\">").append(markdown.getUserName()).append("</font>\n");
        content.append(">产品名称：<font color=\\\"comment\\\">").append(markdown.getProductChName()).append("</font>\n");
        content.append(">产品版本：<font color=\\\"comment\\\">").append(markdown.getProductVersionNumber()).append("</font>\n");
        content.append(">产品型号：<font color=\\\"comment\\\">").append(markdown.getTypeName()).append("</font>\n");
        content.append(">证书编码：<font color=\\\"comment\\\">").append(markdown.getMachineCodeMd()).append("</font>\n");
        content.append(">机器码名：<font color=\\\"comment\\\">").append(markdown.getUploadFileName()).append("</font>\n");
        content.append(">制作人名：<font color=\\\"comment\\\">").append(markdown.getMakeUserName()).append("</font>\n");
        if(markdown.getCertificateState().equals("制作成功")){
            content.append(">制作状态：<font color=\\\"info\\\">").append(markdown.getCertificateState()).append("</font>\n");
        }else{
            content.append(">制作状态：<font color=\\\"warning\\\">").append(markdown.getCertificateState()).append("</font>\n");
        }
        content.append(">制作时间：<font color=\\\"comment\\\">").append(markdown.getMakeTime()).append("</font>\n");
        content.append(">授权期限：<font color=\\\"comment\\\">").append(markdown.getAuthorizationDeadlineTime()).append("</font>\n");
        content.append(">维保期限：<font color=\\\"comment\\\">").append(markdown.getMaintenanceWarnDate()).append("</font>\n");
        if(markdown.getCertificateState().equals("制作成功")){
            content.append(">产品证书：[点击下载](").append(markdown.getFileName()).append(")\n");
        }else{
            content.append(">产品证书：<font color=\\\"warning\\\">").append("证书制作失败，请重新申请制作！").append("</font>\n");
        }
//        content.append(">制作时间：<font color=\\\"comment\\\">").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</font>\n");
    }

    public static void main(String[] args) {
        // 调用发送消息的方法
        String WEBHOOK_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=fba8ce79-c1b0-4fae-ad3d-56c1bd3051ae";
        String msgtype = "markdown";
        String content = "**产品证书一键自动化制作成功，<font color=\\\"warning\\\">共1个</font>，详情如下，请相关同事注意。** \n" +
                ">申请姓名：<font color=\\\"info\\\">+ message.getMarkdown().getName() + </font>\n " +
                ">产品名称：<font color=\\\"comment\\\">+ message.getMarkdown().getProductChName() + </font>\n" +
                ">产品版本：<font color=\\\"comment\\\"> + message.getMarkdown().getProductVersionNumber() + </font>\n " +
                ">产品型号：<font color=\\\"comment\\\"> + message.getMarkdown().getTypeName() + </font>\n" +
                ">证书编码：<font color=\\\"comment\\\"> + message.getMarkdown().getMachineCodeMd() + </font>\n" +
                ">机器码名：<font color=\\\"comment\\\"> + message.getMarkdown().getUploadFileName() + </font>\n" +
                ">产品证书：[点击下载]( + message.getMarkdown().getFileName() + )\n" +
                ">时间：<font color=\\\"info\\\">" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</font>";
        WeChatMessage message = new WeChatMessage();
        message.setUrl(WEBHOOK_URL);
        message.setMsgtype(msgtype);
        WeChatMessage.Markdown markdown = new WeChatMessage.Markdown();
        markdown.setUserName("content");
        markdown.setProductChName("content");
        markdown.setProductVersionNumber("content");
        markdown.setTypeName("content");
        markdown.setMachineCodeMd("content");
        markdown.setUploadFileName("content");
        markdown.setFileName("content");
        message.setMarkdown(markdown);
        sendMessage(message);
    }
}
