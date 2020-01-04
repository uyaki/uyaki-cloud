package com.benyuan.bootdemo.lib.enumeration;

/**
 * @Description
 */
public enum ResultEnum {
    SUCCESS(200, "操作成功"),
    OPERATE_FAIL(666, "操作失败"),

    USER_NAME_ERROR(10001, "用户名错误"),
    USER_PASSWORD_ERROR(10002, "密码错误");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
