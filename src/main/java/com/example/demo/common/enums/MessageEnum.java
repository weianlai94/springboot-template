package com.example.demo.common.enums;

public enum MessageEnum {
    //令牌状态
    TOKEN_STATUS_ERROR(4023, "token有误"), TOKEN_NOT_EXIST(4024, "token有误"),
    TOKEN_IS_NULL(4025, "token为空"), TOKEN_IS_INVALID(4026, "token已经失效"),UID_IS_NULL(4027, "uid参数不能为空"),PLEASE_LOGIN(4028, "请登录后再操作"),
    YOU_CANT_DO_THIS(4030, "您无权操作#You have no right to operate"),
    STATUS_OK(200, "success"),
    WEIXIN_CREATE_QRCODE_ERROR(9007,"微信生成二维码失败"),
    MOBILE_IS_ERROR(1014,"手机号有误"),
    FREQUENT_SENDING(4001, "不要频繁发送短信(时间间隔60秒)"),
    COMMON_UNKNOW_ERROR(500, "未知错误");

    private String message;
    private int code;

    MessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
