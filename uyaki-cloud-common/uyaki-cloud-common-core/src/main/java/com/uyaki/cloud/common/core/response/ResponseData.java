package com.uyaki.cloud.common.core.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author uyaki
 * @date 2019-08-08 15:08
 */
@Getter
@Setter
public class ResponseData {
    private int code = 200;
    private String message = "";
    private Object data;

    public static ResponseData ok(Object data) {
        return new ResponseData(data);
    }

    public static ResponseData fail() {
        return new ResponseData(null);
    }

    public static ResponseData fail(String message) {
        return new ResponseData(message);
    }

    public static ResponseData fail(String message, int code) {
        return new ResponseData(message, code);
    }

    public static ResponseData failByParam(String message) {
        return new ResponseData(message, ResponseCode.PARAM_ERROR_CODE.getCode());
    }

    public ResponseData(Object data) {
        super();
        this.data = data;
    }

    public ResponseData(String message) {
        super();
        this.message = message;
    }

    public ResponseData(String message, int code) {
        super();
        this.message = message;
        this.code = code;
    }

    public ResponseData() {
        super();
    }


}
