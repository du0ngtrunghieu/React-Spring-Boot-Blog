package com.trunghieu.api.category;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.CategoryRequestDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.response.APIResponse;
import com.trunghieu.common.response.CategoryResponse;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created on 10/4/2020.
 * Class: CategoryController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/categories")
@Api(value = "Category APIs")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "Thêm Thể Loại", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping
    public APIResponse<ApiStatusDto> saveCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        return APIResponse.okStatus(categoryService.createCategory(categoryRequestDto));
    }
    @ApiOperation(value = "Tất Cả Thể Loại", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping
    public APIResponse<?> getAllCategory(
            @RequestParam(value = "page", defaultValue = APIConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", defaultValue = APIConstants.DEFAULT_PAGE_SIZE) Integer size
            ){
        PaginationDto paginationDto = new PaginationDto(page,size);
        return APIResponse.okStatus(categoryService.getAllCategory(paginationDto));
    }
    @ApiOperation(value = "Tất cả thể loại cha", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("/nosubcategory")
    public APIResponse<?> findAllNoSub(){
        return APIResponse.okStatus(categoryService.getAllCategoriesNoSub());
    }
    @ApiOperation(value = "Tất Cả Thể Loại Không Phân Trang", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("/initial")
    public APIResponse<?> getAll(){
        return APIResponse.okStatus(categoryService.getAllCategoriesNoPagination());
    }
    @ApiOperation(value = "Find One Category", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("/{id}")
    public APIResponse<CategoryResponse> findOneCategory(@PathVariable("id") Long id){
        return APIResponse.okStatus(categoryService.findOneCategory(id));
    }
    @ApiOperation(value = "Update Category", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PutMapping("/{id}")
    public APIResponse<ApiStatusDto> updateCategory(@PathVariable("id") Long id,@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        return APIResponse.okStatus(categoryService.updateCategory(id,categoryRequestDto));
    }
    @ApiOperation(value = "Delete Category", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping("/{id}")
    public APIResponse<ApiStatusDto> removeCategory(@PathVariable("id") Long id){
        return APIResponse.okStatus(categoryService.deleteCategory(id));
    }
//    @ApiOperation(value = "Delete Categories", response = List.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
//            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
//            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
//            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
//            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
//            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
//    })
//    @DeleteMapping("/delete")
//    public APIResponse<ApiStatusDto> deleteCategories(@PathVariable List<Cate>){
//        return APIResponse.okStatus(categoryService.deleteCategory(id));
//    }
    @ApiOperation(value = "Tạo Slug", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/generate-slug")
    public APIResponse<ApiStatusDto> createSlug(@RequestParam String name){
        return APIResponse.okStatus(categoryService.createSlug(name));
    }
}
