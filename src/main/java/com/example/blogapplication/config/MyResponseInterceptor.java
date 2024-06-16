package com.example.blogapplication.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

@RestControllerAdvice
public class MyResponseInterceptor implements ResponseBodyAdvice<Object> {

    private final CustomMessageSource customMessageSource;

    public MyResponseInterceptor(CustomMessageSource customMessageSource) {
        this.customMessageSource = customMessageSource;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Method method = (Method) returnType.getMember();
        return method.isAnnotationPresent(MessageParameter.class);
    }

    @Override
    public ApiResponse beforeBodyWrite(Object object,
                                       MethodParameter returnType,
                                       MediaType selectedContentType,
                                       Class selectedConverterType,
                                       ServerHttpRequest request,
                                       ServerHttpResponse response) {
        Method method = (Method) returnType.getMember();
        if (method.isAnnotationPresent(MessageParameter.class)) {
            MessageParameter annotation = method.getAnnotation(MessageParameter.class);
            String message = annotation.message();
            String[] sources = annotation.source();
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(object);
            apiResponse.setMessage(buildMessage(message, sources));
            return apiResponse;
        }
        return null;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiResponse {
        private String message;
        private Object data;
    }

    private String buildMessage(String message, String... sources) {
        return switch (sources.length) {
            case 2 -> customMessageSource.get(message,
                    customMessageSource.get(sources[0]),
                    customMessageSource.get(sources[1]));
            case 3 -> customMessageSource.get(message,
                    customMessageSource.get(sources[0]),
                    customMessageSource.get(sources[1]),
                    customMessageSource.get(sources[2]));
            case 4 -> customMessageSource.get(message,
                    customMessageSource.get(sources[0]),
                    customMessageSource.get(sources[1]),
                    customMessageSource.get(sources[2]),
                    customMessageSource.get(sources[3]));
            default -> customMessageSource.get(message,
                    customMessageSource.get(sources[0]));
        };
    }
}
