package com.sakura.test.service;

import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.json.JSONObject;
import com.sakura.test.domain.SmsRequest;
import com.sakura.test.domain.SmsResponse;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static io.restassured.RestAssured.given;

@Service
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);
    private static final String SMS_API_URL = "https://api.example.com/sms/send"; // 替换为实际短信服务商API URL
    private static final String API_ID = "your_api_id"; // 替换为你的API密钥
    private static final String API_KEY = "your_api_key"; // 替换为你的API密钥

    public JSONObject sendSms(SmsRequest request) {
        // 实现短信发送逻辑，根据请求参数发送短信
        // 这里仅作模拟，实际需要对接短信服务商API

        // 示例：构建返回对象
        SmsResponse smsResponse = new SmsResponse();
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(request.getTemplateId()) && StringUtils.isEmpty(request.getTemplateList()) ) {
            throw new IllegalArgumentException("Either 'content' or 'templateId' with 'templateList' must be provided.");
        }
        List<Map<String, Object>> templateList = request.getTemplateList();
        templateList.forEach(template ->{
            if (template.get("id").equals(request.getTemplateId())){
                Map<String, Object> responseParams = (Map<String, Object>) template.get("responseParams");
                log.info("responseParams:{}",responseParams);
                jsonObject.putAll(responseParams);

            }
        });
//        templateList.forEach(template ->{
//            switch (template.get("id").toString()){
//                case "1":
//                    response[0] = (JSONObject) template.get("responseParams");
////                    Map<String, Object> responseParams = (Map<String, Object>) template.get("responseParams");
//                    break;
//                case "2":
//                    // 营销短信类型
//                    break;
//            }
//        });
//        smsResponse.setCode("0");
//        smsResponse.setMsg("成功");
//        smsResponse.setData("【昂楷科技】您的验证码是：6666。请不要把验证码泄露给其他人。");
        return jsonObject;
    }

    public String sendSms1(SmsRequest request) {
        AtomicReference<String> result = new AtomicReference<>("");
        try {
            if (StringUtils.isEmpty(request.getTemplateId()) && StringUtils.isEmpty(request.getTemplateList()) ) {
                throw new IllegalArgumentException("Either 'content' or 'templateId' with 'templateList' must be provided.");
            }
            List<Map<String, Object>> templateList = request.getTemplateList();
            templateList.forEach(template ->{
                if (template.get("id").equals(request.getTemplateId())){
                    if(template.get("method").equals("https") && template.get("type").equals("post")){
                        Object requestBody = "";
                        if(template.get("contentType").toString().contains("application/json")){
                            requestBody = template.get("requestParams");
                        }else if(template.get("contentType").toString().contains("application/x-www-form-urlencoded")){
                            StringBuilder formUrlEncodedBody = new StringBuilder();
                            Map<String, String> requestParams = (Map<String, String>) template.get("requestParams");
                            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                                try {
                                    String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name());
                                    String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
                                    if (formUrlEncodedBody.length() > 0) {
                                        formUrlEncodedBody.append('&');
                                    }
                                    formUrlEncodedBody.append(encodedKey).append('=').append(encodedValue);
                                } catch (UnsupportedEncodingException e) {
                                    throw new RuntimeException("Failed to encode key or value", e); // This should never happen for UTF-8
                                }
                            }
                            // 最终得到的 formUrlEncodedBody 就是 application/x-www-form-urlencoded 格式的请求体
                            requestBody = formUrlEncodedBody.toString();
                        }
                        // 发起请求
                        Response response = given()
                                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                                .contentType(template.get("contentType").toString())
                                .log().all()
                                .request()
                                .body(requestBody)
                                .when()
                                .post(template.get("url").toString());
                        log.info("Response: {}", response.asString());
                        result.set(response.asString());
//                        LinkedHashMap<String, Object> responseParams = (LinkedHashMap<String, Object>) template.get("responseParams");
//                        Iterator<String> key = responseParams.keySet().iterator();
//                        if (key.hasNext()) {
//                            responseParams.put(key.next(), response.jsonPath().get(key.next()));
//                        }
//                        smsResponse.setCode(String.valueOf(response.jsonPath().getInt("code")));
//                        smsResponse.setMessage(response.jsonPath().get("msg"));
//                        smsResponse.setData(response.jsonPath().get("smsid"));
                    }
                }
            });
        } catch (Exception e) {
//            smsResponse.setCode("-1");
//            smsResponse.setMessage("短信发送失败，请检查请求模板参数！");
//            smsResponse.setData("");
            log.error("exception message", e);
        }
        return result.get();
    }

//    public SmsResponse sendSms2(SmsRequest request) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        try {
//            // 构建请求体
//            JSONObject requestBody = new JSONObject();
//            requestBody.set("platformName", request.getPlatformName());
//            requestBody.set("account", request.getAccount());
//            requestBody.set("passWord", request.getPassWord());
//            requestBody.set("sendingMethod", request.getSendingMethod());
//            requestBody.set("URL", request.getUrl());
//            requestBody.set("type", request.getType());
//            requestBody.set("mobileList", request.getMobileList().toArray());
//            requestBody.set("signName", request.getSignName());
//            if (request.getContent() != null) {
//                requestBody.set("text", request.getContent());
//            } else if (request.getTemplateId() != null && request.getTemplateParams() != null) {
//                requestBody.set("templateId", request.getTemplateId());
//                requestBody.set("templateParams", request.getTemplateParams());
//            } else {
//                throw new IllegalArgumentException("Either 'content' or 'templateId' with 'templateParams' must be provided.");
//            }
//
//            // 创建HttpPost请求
//            HttpPost httpPost = new HttpPost(SMS_API_URL);
//            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//            httpPost.setHeader("Authorization", "Bearer " + API_KEY); // 或其他鉴权方式，根据服务商API文档调整
//            httpPost.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));
//
//            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode == 200) {
//                    // 处理成功响应
//                    HttpEntity responseEntity = response.getEntity();
//                    String responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
//                    JSONObject jsonResponse = new JSONObject(responseBody);
//                    // 解析响应中的messageId和status（或其他字段，根据服务商API文档调整）
//                    String messageId = jsonResponse.getStr("message_id");
//                    String status = jsonResponse.getStr("status");
//
//                    SmsResponse smsResponse = new SmsResponse();
//                    smsResponse.setCode("0");
//                    smsResponse.setMessage("成功");
//                    smsResponse.setData("发送成功，请注意查收短信！");
//
//                    return smsResponse;
//                } else {
//                    // 处理失败响应
//                    System.err.printf("Failed to send SMS. Response status code: %d%n", statusCode);
//                    return null;
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error sending SMS: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                httpClient.close();
//            } catch (Exception e) {
//                System.err.println("Error closing HttpClient: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }

}
