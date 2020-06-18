package com.uyaki.cloud.zuul.controller;

import com.uyaki.cloud.common.core.response.ResponseCode;
import com.uyaki.cloud.common.core.response.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 异常返回统一格式
 * @author uyaki
 * @date 2019-08-21 17:56
 */
@RestController
public class ErrorHandlerController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/error")
    public ResponseData error(HttpServletRequest request){
        Map<String,Object> errorAttributes = getErrorAttributes(request);
        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");
        if(StringUtils.isNotBlank(trace)){
            message+=String.format(" and trace %s",trace);
        }
        return ResponseData.fail(message, ResponseCode.SERVER_ERROR_CODE.getCode());
    }
    private Map<String,Object> getErrorAttributes(HttpServletRequest request){
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request),true);
    }
}
