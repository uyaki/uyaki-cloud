package com.uyaki.cloud.common.core.file;

/**
 * 文件异常类
 *
 * @author noone
 * @date 2019-06-27 18:24
 */
public class FileException extends RuntimeException{
    public FileException(String message){
        super(message);
    }
    public FileException(String message,Throwable cause){
        super(message,cause);
    }
}
