package com.benyuan.bootdemo.lib.expection;

import com.benyuan.bootdemo.lib.enumeration.ResultEnum;

/**
 * @Description
 */
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 给自定义的业务异常类用的方法
     * @param responseCodeEnum responseCodeEnum
     */
    public BaseException(ResultEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }

    private BaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
