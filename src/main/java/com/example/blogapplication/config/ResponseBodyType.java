package com.example.blogapplication.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ResponseBodyType implements Serializable {
    ResponseBodyType(String message, Object body){
        this.message = message;
        this.body = body;
    }
    String message;
    Object body;
}
