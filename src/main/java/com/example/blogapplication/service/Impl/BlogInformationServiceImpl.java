package com.example.blogapplication.service.Impl;

import com.example.blogapplication.dto.BlogInformationDto;
import com.example.blogapplication.config.CustomMessageSource;
import com.example.blogapplication.entity.BlogInformation;
import com.example.blogapplication.exception.NotFoundException;
import com.example.blogapplication.generics.NullAwareBeanUtilsBean;
import com.example.blogapplication.repository.BlogInformationRepository;
import com.example.blogapplication.service.BlogInformationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.blogapplication.constants.MessageConstants.BLOG;
import static com.example.blogapplication.constants.MessageConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BlogInformationServiceImpl implements BlogInformationService {

    private final CustomMessageSource customMessageSource;
    private final BlogInformationRepository blogInformationRepository;

    @Override
    public BlogInformationDto createBlog(BlogInformationDto blogInformationDto) {
        BlogInformation blogInformation=toEntity(blogInformationDto);
        BlogInformation saveBlog=blogInformationRepository.save(blogInformation);
        return toDto(saveBlog);
    }

    @Override
    public Integer updateBlog(BlogInformationDto blogInformationDto) throws InvocationTargetException, IllegalAccessException {
        BlogInformation entity = this.findEntityById(blogInformationDto.getId());
        BlogInformation transientObject=this.toEntity(blogInformationDto);
        BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
        beanUtilsBean.copyProperties(entity,transientObject);
        BlogInformation updateEntity = blogInformationRepository.save(entity);
        return updateEntity.getId();
    }

    @Override
    public boolean deleteBlog(Integer id) {
        this.blogInformationRepository.deleteById(id);
        return true;
    }

    @Override
    public List<BlogInformationDto> getAllBlog() {
        List<BlogInformation> entityList = blogInformationRepository.findAll();
        if (entityList.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public BlogInformationDto getBlogById(Integer id) {
        BlogInformation entity = this.findEntityById(id);
        return this.toDto(entity);
    }

    public BlogInformation findEntityById(Integer id){
        return this.blogInformationRepository.findById(id).orElseThrow(()->new NotFoundException(customMessageSource.get(NOT_FOUND,customMessageSource.get(BLOG))));
    }
    private BlogInformationDto toDto(BlogInformation blogInformation){
        BlogInformationDto blogInformationDto=new BlogInformationDto();
        blogInformationDto.setId(blogInformation.getId());
        blogInformationDto.setTitle(blogInformation.getTitle());
        blogInformationDto.setDescription(blogInformation.getDescription());
        blogInformationDto.setCategories(blogInformation.getCategories());
        return blogInformationDto;
    }
    private BlogInformation toEntity(BlogInformationDto blogInformationDto){
        BlogInformation blogInformation=new BlogInformation();
        blogInformation.setTitle(blogInformationDto.getTitle());
        blogInformation.setDescription(blogInformationDto.getDescription());
        blogInformation.setCategories(blogInformationDto.getCategories());
        return blogInformation;
    }
}
