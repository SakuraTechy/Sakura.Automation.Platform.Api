package com.sakura.common.core.domain;

/**
 * @author 刘智
 */
public enum Code {

    OK("0", "OK"),
    ERROR("500", "系统错误"),
    illegalArgument("40001", "参数错误"),
    SESSION_CHECK_ERROR("40002","认证失败"),
    TOKEN_CHECK_ERROR("40003","token认证失败"),
    FIGHTING("40004","哎呀，您来晚了! \n 您的好友已经与\"%s\"开始对战"),
    FIGHT_TO_HOME("40005", "无法开始对战"),
    UPLOAD_TOO_MUACH("40006","上传次数过多"),
    BAG_NOT_EXISTS("40007","卡包不存在"),
    INTEGRATION_NOT_ENOUGHT("40008","积分不足"),
    BUY_BAG_FAIL("40009","购买卡包失败"),
    ACTION_FREQUENT("40010", "操作频繁"),
    ROOM_NOT_EXIST("4011","房间不存在"),
    USER_LEAVE("40012","用户已经离开"),
    ;

    private String code;

    private String desc;

    Code(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}