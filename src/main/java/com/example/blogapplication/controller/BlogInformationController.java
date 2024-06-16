package com.example.blogapplication.controller;

import com.example.blogapplication.config.MessageParameter;
import com.example.blogapplication.dto.BlogInformationDto;
import com.example.blogapplication.service.BlogInformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.example.blogapplication.constants.MessageConstants.*;

@RestController
@RequestMapping("blog")
@RequiredArgsConstructor
public class BlogInformationController {

    private final BlogInformationService blogInformationService;

    @PostMapping()
    @MessageParameter(message = SUCCESS_CREATE, source = BLOG)
    public BlogInformationDto create(@RequestBody @Valid BlogInformationDto blogInformationDto){
        return blogInformationService.createBlog(blogInformationDto);
    }

    @GetMapping("/all")
    @MessageParameter(message = SUCCESS_FETCH, source = BLOG)
    public List<BlogInformationDto> getAllBlogInformation(){
        return blogInformationService.getAllBlog();
    }

    @GetMapping("/{id}")
    @MessageParameter(message = SUCCESS_FETCH, source = BLOG)
    public BlogInformationDto getBlogInformationById(@PathVariable(name = "id") Integer id){
        return blogInformationService.getBlogById(id);
    }

    @PutMapping()
    @MessageParameter(message = SUCCESS_UPDATE, source = BLOG)
    public Integer updateBlogInformation(@RequestBody BlogInformationDto blogInformationDto) throws InvocationTargetException, IllegalAccessException {
        return blogInformationService.updateBlog(blogInformationDto);
    }

    @DeleteMapping("/{id}")
    @MessageParameter(message = SUCCESS_DELETE, source = BLOG)
    public boolean deleteBlogInformation(@PathVariable(name = "id") Integer id){
        return blogInformationService.deleteBlog(id);
    }
}
