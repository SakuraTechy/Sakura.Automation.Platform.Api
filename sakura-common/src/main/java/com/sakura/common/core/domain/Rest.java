/*
 * Copyright (C) 2017 Zhejiang BYCDAO Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.bycdao.com.
 * Developer Web Site: http://open.bycdao.com.
 */

package com.sakura.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 *
 * @since:swagger-bootstrap-ui-demo 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 *         2017/08/21 16:41
 */
@Data
public class Rest<T> {

    /**
     * 用于类字段 表示对model属性的说明或者数据操作更改
     * value–字段说明 
     * name–重写属性名字 
     * dataType–重写属性类型 
     * required–是否必填 
     * example–举例说明 
     * hidden–隐藏
     */
    @ApiModelProperty(value = "是否成功")
    private boolean success = true;
    @ApiModelProperty(value = "返回对象")
    private T data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // public T getData() {
    //     return data;
    // }

    // public void setData(T data) {
    //     this.data = data;
    // }

    // public Integer getErrCode() {
    //     return errCode;
    // }

    // public void setErrCode(Integer errCode) {
    //     this.errCode = errCode;
    // }

    // public String getMessage() {
    //     return message;
    // }

    // public void setMessage(String message) {
    //     this.message = message;
    // }

    public static <T> Rest<T> data(T data) {
        Rest<T> rest = new Rest<>();
        rest.setData(data);
        rest.setErrCode(8200);
        rest.setSuccess(true);
        rest.setMessage("操作成功");
        return rest;
    }
}
