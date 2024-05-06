package com.sakura.common.core.domain;

import com.sakura.common.constant.Constants;
import com.sakura.common.constant.HttpStatus;

import java.io.Serializable;

/**
 * 响应信息主体
 * @author liuzhi
 */
public class R1<T>  implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.FAIL;

    private int code;

    private String msg;

    private T data;

    public R1() {};

    public R1(T data, int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R1(int code, String msg) {
        this.code = code;
        this.msg = msg;
        //this.data = data;
    }


    public static <T> R1<T> success()
    {
        return new R1(null, SUCCESS, null);
    }

    public static <T> R1<T> success(String msg)
    {
        return new R1(null, SUCCESS, msg);
    }

    public static <T> R1<T> data(T data)
    {
        return new R1(data, SUCCESS, null);
    }

    public static <T> R1<T> data(T data, String msg)
    {
        return new R1(data, SUCCESS, msg);
    }

    public static <T> R1<T> fail(String msg)
    {
        return new R1(FAIL, msg);
    }

    public static <T> R1<T> fail(T data)
    {
        return new R1(data, FAIL, null);
    }

    public static <T> R1<T> fail(T data, String msg)
    {
        return new R1(data, FAIL, msg);
    }

    public static <T> R1<T> fail(int code, String msg)
    {
        return new R1(null, code, msg);
    }

    public static <T> R1<T> status(boolean flag) {
        return flag ? success(Constants.DEFAULT_SUCCESS_MESSAGE) : fail(Constants.DEFAULT_FAILURE_MESSAGE);
    }

    public static <T> R1<T> status(int rows) {
        return rows > 0 ? success(Constants.DEFAULT_SUCCESS_MESSAGE) : fail(Constants.DEFAULT_FAILURE_MESSAGE);
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
