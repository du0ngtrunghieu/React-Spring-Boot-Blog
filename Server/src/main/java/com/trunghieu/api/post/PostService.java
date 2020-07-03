package com.trunghieu.api.post;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.PostRequestDto;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.Category;
import com.trunghieu.common.model.Post;
import com.trunghieu.common.repository.CategoryRepository;
import com.trunghieu.common.repository.PostRepository;
import com.trunghieu.common.response.CategoryResponse;
import com.trunghieu.common.response.PostResponse;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 11/4/2020.
 * Class: PostService.java
 * By : Admin
 */
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public ApiStatusDto createPost(PostRequestDto postRequestDto){
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        postRequestDto.getCategoryId().forEach(id ->{
            CategoryResponse categoryResponse = new CategoryResponse();
            Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Id",id));
            if(!category.getSubCategories().isEmpty()){
                throw new BadRequestException(String.format("Thể loại id = %s này có subcategories ,Vui lòng chọn thể loại khác !",id));
            }
            BeanUtils.copyProperties(category,categoryResponse);

            categoryResponses.add(categoryResponse);
            categories.add(category);
        });
        Post post = new Post();
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(postRequestDto,post);
        post.setCategories(categories);
        Post rs = postRepository.save(post);
        BeanUtils.copyProperties(rs,postResponse);
        postResponse.setCategories(categoryResponses);
        return new ApiStatusDto(true, MessageConstants.POST_REGISTER_SUCCESS,postResponse);
    }
}
