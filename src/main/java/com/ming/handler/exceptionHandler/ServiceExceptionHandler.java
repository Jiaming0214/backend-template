package com.ming.handler.exceptionHandler;

import com.ming.entity.RestBean;
import com.ming.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public RestBean<String> handleServiceException(ServiceException e) {
        return RestBean.failure(e.getCode(), e.getMessage());
    }

}
