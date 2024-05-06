package com.sakura.web.controller.test;

import com.sakura.common.utils.StringUtils;
import com.sakura.test.domain.SmsRequest;
import com.sakura.test.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

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
}
