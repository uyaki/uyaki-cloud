package com.benyuan.bootdemo.lib.handler;

import com.benyuan.bootdemo.lib.enumeration.ResultEnum;
import com.benyuan.bootdemo.lib.expection.BaseException;
import com.benyuan.bootdemo.lib.util.ResponseDTO;
import com.benyuan.bootdemo.lib.util.ResponseDTOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     *@description 处理所有不可知的异常(系统异常)
     */
    @ExceptionHandler({Exception.class})
    public ResponseDTO systemException(Exception e) {
        this.logger.error(e.getMessage(), e);
        return ResponseDTOUtil.error(ResultEnum.OPERATE_FAIL);
    }

    /**
     *@description 处理自定义的业务异常
     *  BaseException 类继承 RuntimeException
     */
    @ExceptionHandler({BaseException.class})
    public ResponseDTO businessException(BaseException e) {
        this.logger.error(e.getMessage(), e);
        return ResponseDTOUtil.error(e.getCode(), e.getMessage());
    }
}
