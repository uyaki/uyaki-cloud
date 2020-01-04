package com.benyuan.bootdemo.lib.util;

import lombok.*;

/**
 * @Description ResponseDTO通用类
 */
@Data
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

}
