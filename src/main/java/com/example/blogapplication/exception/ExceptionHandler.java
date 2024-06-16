package com.example.blogapplication.exception;

import com.example.blogapplication.config.CustomMessageSource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class ExceptionHandler<T> {
    private static final List<String> SHOW_ERROR_BASE_PACKAGES = List.of("com.example.blogapplication");
    protected CustomMessageSource customMessageSource;

    @ResponseBody
    protected abstract ResponseEntity<Object> handleException(
            T ex, WebRequest request);

    protected void generateExceptionLog(String methodName, Exception ex) {
        log.error("Error handler invoked: {}", methodName);
        log.error("Error Messaged: {}", ex.getMessage());
        Stream.of(ex.getStackTrace())
                .filter(stackTraceElement ->
                        SHOW_ERROR_BASE_PACKAGES.parallelStream()
                                .anyMatch(packageName -> stackTraceElement.getClassName()
                                        .startsWith(packageName)
                                ) && stackTraceElement.getLineNumber() > 0)
                .forEach(element ->
                        log.error("Error in Class: {} Method: {} Line No: {}", element.getClassName(), element.getMethodName(), element.getLineNumber())
                );
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                return null;
            }
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            request.setAttribute("jakarta.servlet.error.exception", ex, 0);
        }

        return this.createResponseEntity(body, headers, statusCode, request);
    }

    protected ResponseEntity<Object> createResponseEntity(@Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(body, headers, statusCode);
    }
}
