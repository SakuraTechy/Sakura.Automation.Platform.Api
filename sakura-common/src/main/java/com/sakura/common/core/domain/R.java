package com.sakura.common.core.domain;

import java.io.Serializable;
import java.util.Map;

import com.sakura.common.constant.Constants;
import com.sakura.common.core.domain.model.CaptchaBody;
import com.sakura.common.utils.json.JSONObject;

import com.sakura.common.utils.spring.SpringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description: 接口请求返回数据模型
 * @author: liuzhi
 * @date: 2018年5月15日 上午11:55:24
 */
@Data
@ApiModel("统一消息返回数据模型")
public class R<T> implements Serializable {
    /**
     * 返回状态
     */
    @ApiModelProperty(value = "返回状态", required = true, position = 0, example = "" + SUCCESS + "")
    private int code;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息", required = true, position = 10, example = MESSAGE_SUCCESS)
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据", required = true, position = 20, example = "")
    // @ApiModelProperty(value = "返回数据",position = 20,example = "{\"name\": \"Jack\"}")
    private T data;


    /**
     * 返回数据
     */

    @ApiModelProperty(value = "版本号", required = true, position = 20, example = "v1.0.0")
    private String version;


    // ------------------- 成功、失败返回码-----------------------/
    /**
     * 执行成功状态
     */
    @ApiModelProperty(value = "成功返回")
    private static final int SUCCESS = 200;

    /**
     * 执行失败状态
     */
    private static final int ERROR = 500;

    // ------------------- 参数错误返回码：10001-19999-----------------/
    /**
     * 参数为空
     */
    public static final int PARAM_IS_BLANK = 10001;

    /**
     * 参数类型错误
     */
    public static final int PARAM_TYPE_ERROR = 10002;

    /**
     * 参数缺失
     */
    public static final int PARAM_NOT_COMPLETE = 10003;

    // -------------------登录注册错误：20001-29999-----------------/
    /**
     * 用户未登录
     */
    public static final int USER_NOT_LOGGED_IN = 20001;

    /**
     * 用户不存在
     */
    public static final int USER_NOT_EXIST = 20002;

    /**
     * 用户已存在
     */
    public static final int USER_HAS_EXISTED = 20003;

    /**
     * 账号密码错误
     */
    public static final int USER_LOGIN_ERROR = 20004;

    /**
     * 账号已被禁用
     */
    public static final int USER_ACCOUNT_FORBIDDEN = 20005;

    // -------------------业务错误：20001-29999-----------------/
    /**
     * 业务错误码
     */
    public static final int BUSINESS_ERROR_CODE = 20001;

    /**
     * 业务成功，通知失败
     */
    public static final int NOTICE_ERROR_BUSINESS_SUCCESS = 3001;

    /**
     * 成功消息
     */
    public static final String MESSAGE_SUCCESS = "请求成功";

    /**
     * 失败消息
     */
    public static final String MESSAGE_ERROR = "请求失败";

    // -------------------当前版本版本号-----------------/
    /**
     * 当前版本号
     */
    public static final String VERSION_NUM = SpringUtils.getRequiredProperty("sakura.version");

    public R(final int code, final String msg, final String version) {
        this.code = code;
        this.msg = msg;
        this.version = version;
    }

    public R(final int code, final String msg, final String version, final T data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = data;
    }

    public R(final int code, final String msg, final String version, final Map<String, String> data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = (T) data;
    }

    public R(final int code, final String msg, final String version, final JSONObject data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = (T) data;
    }

    public R(final int code, final String msg, final String version, final Exception data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = (T) data;
    }

    public R(final int code, final String msg, final String version, final RuntimeException data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = (T) data;
    }

    /**
     * 成功响应，date为空
     *
     * @return
     */
    public static <T> R<T> success() {
        return new R<>(SUCCESS, MESSAGE_SUCCESS, VERSION_NUM);
    }

    public static <T> R<T> success(final String msg) {
        return new R<>(SUCCESS, msg, VERSION_NUM);
    }

    public static <T> R<T> success(final T data) {
        return new R<>(SUCCESS, MESSAGE_SUCCESS, VERSION_NUM, data);
    }

    public static <T> R<T> success(final Map<String, String> data) {
        return new R<>(SUCCESS, MESSAGE_SUCCESS, VERSION_NUM, data);
    }

    public static <T> R<T> success(final JSONObject data) {
        return new R<>(SUCCESS, MESSAGE_SUCCESS, VERSION_NUM, data);
    }

    public static <T> R<T> data(T data) {
        return new R(SUCCESS, MESSAGE_SUCCESS, VERSION_NUM, data);
    }

    /**
     * 失败响应
     *
     * @return
     */
    public static <T> R<T> error() {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM);
    }

    public static <T> R<T> error(final String msg) {
        return new R<>(ERROR, msg, VERSION_NUM);
    }

    public static <T> R<T> error(final T data) {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM, data);
    }

    public static <T> R<T> error(final Map<String, String> data) {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM, data);
    }

    public static <T> R<T> error(final JSONObject data) {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM, data);
    }

    public static <T> R<T> error(final Exception data) {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM, data);
    }

    public static <T> R<T> error(final RuntimeException data) {
        return new R<>(ERROR, MESSAGE_ERROR, VERSION_NUM, data);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(ERROR, msg, VERSION_NUM);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, msg, VERSION_NUM);
    }

    public static <T> R<T> fail(T date, String msg) {
        return new R<>(ERROR, msg, VERSION_NUM, date);
    }

    /**
     * 自定义响应
     *
     * @return
     */
    public static <T> R<T> custom(final int code, final String msg, final T data) {
        return new R<>(code, msg, VERSION_NUM, data);
    }

    public static <T> R<T> custom(final int code, final String msg, final Map<String, String> data) {
        return new R<>(code, msg, VERSION_NUM, data);
    }

    public static <T> R<T> custom(final int code, final String msg, final JSONObject data) {
        return new R<>(code, msg, VERSION_NUM, data);
    }

    public static <T> R<T> status(boolean flag) {
        return flag ? success(MESSAGE_SUCCESS) : error(MESSAGE_ERROR);
    }

    public static <T> R<T> status(int rows) {
        return rows > 0 ? success(MESSAGE_SUCCESS) : error(MESSAGE_ERROR);
    }

    @Override
    public String toString() {
        return "R [code=" + code + ", msg=" + msg + ", version=" + version + ", data=" + data + "]";
    }

}