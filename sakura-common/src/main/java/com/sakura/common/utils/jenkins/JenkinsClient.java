package com.sakura.common.utils.jenkins;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Base64;

public class JenkinsClient {
    public static void main(String[] args) throws Exception {
        String jenkinsUrl = "http://172.19.5.222:8080";
        String username = "sakura";
        String password = "3edc$RFV";
        String nodeId = "172.19.5.230";

        // 构建HTTP客户端
        HttpClient client = HttpClientBuilder.create().build();

        // 构建GET请求
        HttpGet request = new HttpGet(jenkinsUrl + "/computer/" + nodeId + "/config.xml");

        // 设置认证信息
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        request.setHeader("Authorization", "Basic " + encodedAuth);

        // 发送请求并获取响应
        HttpResponse response = client.execute(request);

        // 解析响应内容
        String xmlResponse = EntityUtils.toString(response.getEntity());

        // 打印节点配置信息
        System.out.println(xmlResponse);
    }
}