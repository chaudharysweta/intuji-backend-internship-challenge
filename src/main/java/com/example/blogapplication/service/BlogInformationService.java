package com.example.blogapplication.service;

import com.example.blogapplication.dto.BlogInformationDto;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface BlogInformationService {

    //create
    BlogInformationDto createBlog(BlogInformationDto blogInformationDto);

    //update
    Integer updateBlog(BlogInformationDto blogInformationDto) throws InvocationTargetException, IllegalAccessException;

    //delete
    boolean deleteBlog(Integer id);

    //get all
    List<BlogInformationDto> getAllBlog();

    //get by id
    BlogInformationDto getBlogById(Integer id);
}
