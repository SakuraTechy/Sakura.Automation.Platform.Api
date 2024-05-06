package com.sakura.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回调数据")
    private T data;

    @ApiModelProperty(value = "版本号")
    private String vision;

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "消息提示")
    private String msg;

    @ApiModelProperty(value = "数据结果集")
    public T getData() {
        return data;
    }

    public String getVision() {
        return vision;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResult<T> setVision(String vision) {
        this.vision = vision;
        return this;
    }

    public ApiResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public ApiResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * @Description 成功回调
     * @param data   回调数据
     * @param vision 版本号
     * @return
     */
    public static ApiResult successMsg(Object Object, String vision) {
        return new ApiResult().setCode("200").setMsg("scuess").setData(Object).setVision(vision);
    }

}
