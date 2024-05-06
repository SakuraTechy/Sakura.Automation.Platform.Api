package com.sakura.test.domain;
import lombok.Data;

@Data
public class SmsResponse {

    private String code;
    private String msg;
    private String data;

}
