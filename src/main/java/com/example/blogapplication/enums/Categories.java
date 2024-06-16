package com.example.blogapplication.enums;

import com.example.blogapplication.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Categories {

    TECHNOLOGY(1,"TECHNOLOGY"),
    LIFESTYLE(2,"LIFESTYLE"),
    EDUCATION(3,"EDUCATION"),
    HEALTH(4,"HEALTH"),
    BUSINESS(5,"BUSINESS"),
    ENTERTAINMENT(6,"ENTERTAINMENT"),
    FOOD(7,"FOOD"),
    ENVIRONMENT(8,"ENVIRONMENT");

    private Integer key;
    private String value;

    public static Categories findByKey(Integer key) throws NotFoundException {
        for (Categories status : Categories.values()) {
            if (status.key.equals(key)) {
                return status;
            }
        }
        throw new NotFoundException("given key not found");
    }

    public static Categories findByValue(String value) throws NotFoundException {
        for (Categories status : Categories.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new NotFoundException("given value not found");
    }


}
