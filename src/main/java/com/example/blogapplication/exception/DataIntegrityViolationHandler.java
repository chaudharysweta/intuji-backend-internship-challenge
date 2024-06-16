package com.example.blogapplication.exception;

import com.example.blogapplication.config.CustomMessageSource;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;

/**
 * @author nitesh thapa
 * @created 12/5/2023
 * @project Gym-management-backend
 */

@ControllerAdvice
public class DataIntegrityViolationHandler extends ExceptionHandler<DataIntegrityViolationException> {

    private final CustomMessageSource customMessageSource;

    public DataIntegrityViolationHandler(CustomMessageSource customMessageSource) {
        this.customMessageSource = customMessageSource;
    }

    @Override
    @org.springframework.web.bind.annotation.ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleException(DataIntegrityViolationException ex, WebRequest request) {
        generateExceptionLog("DataIntegrityViolationException", ex);
        String violatedConstraintMessage;
        if (ex.getCause() instanceof ConstraintViolationException violation) {
            String constraintName = violation.getConstraintName();
            assert constraintName != null;
            List<String> stringList = Arrays.stream(constraintName.split("_")).map(String::toLowerCase).toList();
            String key = stringList.get(stringList.size() - 1);

            if (constraintName.contains("uk_")) {
                violatedConstraintMessage = customMessageSource.get("violated.unique.constraint", customMessageSource.get(key));
            } else if (constraintName.contains("fk_")) {
                violatedConstraintMessage = customMessageSource.get("violated.foreign.constraint", customMessageSource.get(key));
            } else if (constraintName.contains("check_")) {
                violatedConstraintMessage = customMessageSource.get("violated.check.constraint", customMessageSource.get(key));
            } else {
                violatedConstraintMessage = customMessageSource.get("please.contact.operator");
            }
        } else if (ex.getCause() instanceof DataException) {
            if (ex.getMessage().contains("value too long"))
                violatedConstraintMessage = customMessageSource.get("data.length.validation");
            else
                violatedConstraintMessage = customMessageSource.get("invalid.data.validation");
        } else {
            violatedConstraintMessage = customMessageSource.get("please.contact.operator");
        }
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, HttpStatus.INTERNAL_SERVER_ERROR.value(), violatedConstraintMessage, violatedConstraintMessage);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
