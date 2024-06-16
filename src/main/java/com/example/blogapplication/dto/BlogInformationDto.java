package com.example.blogapplication.dto;

import com.example.blogapplication.enums.Categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BlogInformationDto {

    private Integer id;

    @NotNull(message = "Tittle cannot be null")
    @Size(message = "Title between 3 to 100", max = 100)
    private String title;

    @NotBlank(message = "Description must not be empty")
    private String description;

    @NotNull(message = "Category cannot be empty")
    private Categories categories;


}
