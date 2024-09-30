package com.sakura.web.controller.test;

import com.sakura.common.utils.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sakura.test.domain.SmsRequest;
import com.sakura.test.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sendSms")
    public Object sendSms(@RequestBody SmsRequest request) {
        if (!request.getTemplateId().equals("1")) {
            return smsService.sendSms(request);
        }else{
            return smsService.sendSms1(request);
        }
    }

    @PostMapping(value = "/SMSServer/sendFullTextSms",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object sendFullTextSms(@RequestBody SmsRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        jsonObject.put("msgid", "msgid");
        jsonObject.put("errPhone", "110,112,123456");
        jsonObject.put("blackPhone", "13812345678,13812345679");
        return jsonObject;
    }

    @PostMapping(value = "/api/v2/single/send",consumes = MediaType.APPLICATION_XML_VALUE)
    public Object single(@RequestBody SmsRequest request) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 2);
        jsonObject.put("msg", "SUCCESS");
        jsonObject.put("smUUid", "xxxx");
        return jsonObject.toXml(jsonObject.toString());
    }
    @PostMapping(value ="/mt" ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Object mt(
            @RequestParam("name") String name,
            @RequestParam("accesskey") String accesskey,
            @RequestParam("secret") String secret,
            @RequestParam("sign") String sign,
            @RequestParam("templateid") String templateid,
            @RequestParam("mobile") String mobile,
            @RequestParam("content") String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("id", "132421411241241241");
        return jsonObject;
    }

    @PostMapping("/webservice/sms.php?method=Submite")
    public Object webservice(@RequestBody SmsRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 2);
        jsonObject.put("msg", "msg");
        jsonObject.put("smsid", "132421411241241241");
        return jsonObject;
    }

    @GetMapping("/webservice/sms.php")
    public JSONObject submitSms(
            @RequestParam(name = "method", required = true) String method,
            @RequestBody SmsRequest request) {

        JSONObject jsonObject = new JSONObject();
        if ("Submit".equals(method)) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "msg");
            jsonObject.put("smsid", "132421411241241241");
        } else {
            jsonObject.put("msg", "Invalid method specified.");
        }
        return jsonObject;
    }

    @PostMapping("/yktsms/send")
    public Object yktsms(@RequestBody SmsRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 2);
        jsonObject.put("msg", "msg");
        jsonObject.put("taskid", "taskid");
        return jsonObject;
    }

    @PostMapping("/tp_mp/service/SmsService?wsdle")
    public Object SmsService(@RequestBody SmsRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", true);
        jsonObject.put("msg", "msg");
        jsonObject.put("msg_id", "msg_id");
        return jsonObject;
    }

    @PostMapping("/donn/inder.php")
    public Object donn(@RequestBody SmsRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RESULT_CODE", 2);
        jsonObject.put("RESULT_MSG", "RESULT_MSG");
        jsonObject.put("OrderNumber", "OrderNumber");
        return jsonObject;
    }
}