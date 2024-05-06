package com.sakura.test.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SmsRequest {

//    private String sender; // 发送方电话号码或标识符
//    private List<String> recipients; // 接收方电话号码列表
//    private String content; // 短信内容，直接发送文本消息时使用
//    private String templateId; // 短信模板ID，使用模板发送时使用
//    private Map<String, String> templateParams; // 模板参数，与模板ID一起使用时填充模板占位符

    private String id; // 短信平台名称
    private String name; // 短信平台名称
    private String account; // 短信平台API_ID
    private String passWord; // 短信平台API_KEY
    private String method; // 短信发送方式(HTTP,HTTPS,JDBC)
    private String url; // 短信接口URL
    private String type; // 短信接口请求方式（GET,POST）
    private String contentType; // 短信接口请求数据格式（application/json,application/x-www-form-urlencoded）
    private List<String> mobileList; // 接收方电话号码列表
    private String signName; // 短信平台签名（短信签名是加在短信的开头或结尾，在【】加上您的公司名称或店铺名称的标识符，例如:【互亿无线】。 根据电信基础运营商的规定，每条短信必须附加短信签名，否则将无法正常发送。）
    private String content; // 短信内容，直接发送文本消息时使用
    private String format; // 返回格式（可选值为：json或xml，系统默认为json）
    private String templateId; // 短信模板ID，使用模板发送时使用
    private List<Map<String, Object>> templateList; // 模板参数，与模板ID一起使用时填充模板占位符
}
