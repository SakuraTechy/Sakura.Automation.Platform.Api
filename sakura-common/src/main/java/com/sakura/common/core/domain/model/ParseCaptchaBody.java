package com.sakura.common.core.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 刘智
 * @date 2021/6/15
 * @Description:
 */

@Data
@ApiModel(value = "解析验证码对象模型")
public class ParseCaptchaBody {
    @ApiModelProperty(value = "python路径", required = true, example = "D:/python.exe")
    private String pythonPath;

    @ApiModelProperty(value = "python脚本",required = true, example = "D:/code.py")
    private String pythonScript;

    @ApiModelProperty(value = "验证码图片",required = true, example = "D:/code.png")
    private String captchaUrl;

    @ApiModelProperty(value = "验证码路径",required = true, example = "D:/code.png")
    private String captchaPath;

    @ApiModelProperty(value = "验证码保存",required = true, example = "code.txt")
    private String captchaSave;
}