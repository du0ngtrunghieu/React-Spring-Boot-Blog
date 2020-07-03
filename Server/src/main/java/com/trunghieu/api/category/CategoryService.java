package com.trunghieu.api.category;


import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.CategoryRequestDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.Category;
import com.trunghieu.common.repository.CategoryRepository;
import com.trunghieu.common.response.CategoryResponse;
import com.trunghieu.common.response.PagedResponse;
import com.trunghieu.common.util.CommonUtils;
import com.trunghieu.common.util.PagedUtils;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public PagedResponse<CategoryResponse> getAllCategory(PaginationDto paginationDto){
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize(), Sort.Direction.DESC, "createdAt");
        Page<Category> categories = categoryRepository.findALlByOrderByIdDesc(pageable);
        if(categories.getNumberOfElements() == 0){
            return new PagedResponse<>(Collections.emptyList(),categories.getNumber(),categories.getSize(),
                    categories.getTotalElements(),categories.getTotalPages(),categories.isLast()
                    );
        }
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category->{
                    CategoryResponse categoryResponse = new CategoryResponse();
                    BeanUtils.copyProperties(category,categoryResponse);
                    return categoryResponse;
                }).collect(Collectors.toList());
        return new PagedResponse<>(categoryResponses,categories.getNumber(),categories.getSize(),
                categories.getTotalElements(),categories.getTotalPages(),categories.isLast());
    }
    public List<CategoryResponse> getAllCategoriesNoPagination(){
        List<Category> category = categoryRepository.findAllByOrderByIdDesc();
        return category.stream().map(cate ->{
            CategoryResponse categoryResponse = new CategoryResponse();
            BeanUtils.copyProperties(cate,categoryResponse);
            return categoryResponse;
        }).collect(Collectors.toList());
    }
    public List<CategoryResponse> getAllCategoriesNoSub(){
        List<Category> categories = categoryRepository.findAllByOrderByIdDesc();
        return categories.stream()
                .filter(x -> x.getParent_id() == null)
                .map(category->{
                    CategoryResponse categoryResponse = new CategoryResponse();
                    BeanUtils.copyProperties(category,categoryResponse);
                    return categoryResponse;
                }).collect(Collectors.toList());
    }
    public ApiStatusDto createCategory(CategoryRequestDto categoryRequestDto){
        if(categoryRequestDto.getParent_id() == 0){
            categoryRequestDto.setParent_id(null);
        }
        if(categoryRequestDto.getParent_id() != null){

            Category category = categoryRepository.findById(categoryRequestDto.getParent_id())
                    .orElseThrow(
                            ()->new ResourceNotFoundException("Category","Id",categoryRequestDto.getParent_id())
                    );
            if(category.getParent_id() != null){
                throw new BadRequestException("Thể Loại này là 1 subcategory ! ,Không thể thêm vào thể loại này ! ");
            }
        }
        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(categoryRequestDto,category);
        Category rs = categoryRepository.save(category);
        BeanUtils.copyProperties(rs,categoryResponse);
        return new ApiStatusDto(true, MessageConstants.CATEGORY_REGISTER_SUCCESS,categoryResponse);
    }
    public ApiStatusDto updateCategory(Long id,CategoryRequestDto categoryRequestDto){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category","Id",id)
        );
        if(category.getParent_id() != null){
            throw new BadRequestException("Thể Loại này là 1 subcategory ! ,Không thể thêm vào thể loại này !");
        }
        if(category.getId().equals(categoryRequestDto.getParent_id())){
            throw new BadRequestException("Không thể thêm vào chính nó !");
        }
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(categoryRequestDto,category);
        Category rs = categoryRepository.save(category);
        BeanUtils.copyProperties(rs,categoryResponse);
        return new ApiStatusDto(true, MessageConstants.CATEGORY_UPDATE_SUCCESS,categoryResponse);
    }
    public ApiStatusDto deleteCategory(Long id){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category","Id",id)
        );
        if(!category.getSubCategories().isEmpty()){
            throw new BadRequestException("Xoá Thất Bại !, Thể Loại này có sub category không được xoá !");
        }
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(category,categoryResponse);
        categoryRepository.delete(category);
        categoryResponse.setSubCategories(null);
        return new ApiStatusDto(true, MessageConstants.CATEGORY_DELETE_SUCCESS,categoryResponse);
    }
    public CategoryResponse findOneCategory(Long id){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category","Id",id)
        );
        CategoryResponse categoryResponse = new CategoryResponse();
        if(category.getParent_id() != null){
            Category categoryParent = categoryRepository.findById(category.getParent_id()).orElseThrow(
                    ()-> new ResourceNotFoundException("Category","Id",id)
            );
            categoryResponse.setNameParent(categoryParent.getName());
        }
        BeanUtils.copyProperties(category,categoryResponse);
        return categoryResponse;
    }
//    public List<CategoryResponse> deleteCategories(List<CategoryResponse> categories){
//        List<CategoryResponse> categoryResponses = new ArrayList<>();
//        categories.forEach(category -> {
//            if(categoryRepository.existsById(category.getId())){
//                if(category.get)
//            }
//        });
//        return categoryResponses;
//    }
    public ApiStatusDto createSlug(String name){
        return new ApiStatusDto(true,"",CommonUtils.genarateSlug(name));
    }
}
