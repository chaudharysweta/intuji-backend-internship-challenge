package com.example.blogapplication.exception;


import com.example.blogapplication.config.CustomMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.logging.Logger;

@ControllerAdvice
public class NotFoundExceptionHandler extends ExceptionHandler<NotFoundException> {
    private final CustomMessageSource customMessageSource;
    private final Logger logger = Logger.getLogger(NotFoundExceptionHandler.class.getName());

    public NotFoundExceptionHandler(CustomMessageSource customMessageSource) {
        this.customMessageSource = customMessageSource;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value
            = {NotFoundException.class})
    protected ResponseEntity<Object> handleException(
            NotFoundException ex, WebRequest request) {
        logger.info(ex.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ex.getMessage(), customMessageSource.get("please.contact.operator"));
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }

}
