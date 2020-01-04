package com.benyuan.bootdemo.lib.util;

import com.benyuan.bootdemo.lib.enumeration.ResultEnum;

/**
 * @Description
 */
public class ResponseDTOUtil {
    /**
     * @description 请求成功返回对象
     */
    public static ResponseDTO success() {
        return success(null);
    }

    /**
     * @param data      返回的数据
     * @return 返回成功，包含code、message、data
     */
    public static ResponseDTO success(Object data) {
        int code = ResultEnum.SUCCESS.getCode();
        String message = ResultEnum.SUCCESS.getMessage();
        return success(code, message, data);
    }

    /**
     * @param code      响应码
     * @param message   相应信息
     * @param data      返回的数据
     * @return          返回成功，包含code、message、data
     */
    public static ResponseDTO  success(int code, String message, Object data) {
        return new ResponseDTO(code, message, data);
    }

    /**
     * @param responseCode  返回的响应码所对应的枚举类
     * @return              返回失败，包含code、message、data
     */
    public static ResponseDTO error(ResultEnum responseCode) {
        return new ResponseDTO(responseCode.getCode(), responseCode.getMessage(), null);
    }

    /**
     * @param code      响应码
     * @param message   相应信息
     * @return          返回失败，包含code、message、data
     */
    public static ResponseDTO error(int code, String message) {
        return new ResponseDTO(code, message, null);
    }

}
