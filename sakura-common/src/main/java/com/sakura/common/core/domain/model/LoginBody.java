package com.sakura.common.core.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录对象
 * 
 * @author liuzhi
 */
@Data
@ApiModel(value = "用户登录对象模型")
public class LoginBody {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", required = true, example = "admin123")
    private String passWord;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true, example = "23")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "唯一标识", required = true, example = "1d2d249387cc4572a27271d04294e656")
    private String uuid = "";

    // public String getUsername() {
    //     return userName;
    // }

    // public void setUsername(String userName) {
    //     this.userName = userName;
    // }

    // public String getPassword() {
    //     return passWord;
    // }

    // public void setPassword(String passWord) {
    //     this.passWord = passWord;
    // }

    // public String getCode() {
    //     return code;
    // }

    // public void setCode(String code) {
    //     this.code = code;
    // }

    // public String getUuid() {
    //     return uuid;
    // }

    // public void setUuid(String uuid) {
    //     this.uuid = uuid;
    // }
}
