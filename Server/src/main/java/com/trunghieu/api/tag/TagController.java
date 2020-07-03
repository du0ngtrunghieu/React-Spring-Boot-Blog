package com.trunghieu.api.tag;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.CategoryRequestDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.dto.TagRequestDto;
import com.trunghieu.common.response.APIResponse;
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
 * Created on 25/5/2020.
 * Class: TagController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/tags")
@Api(value = "Tag APIs")
public class TagController {
    @Autowired
    TagService tagService;

    @ApiOperation(value = "Thêm Tag", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping
    public APIResponse<ApiStatusDto> saveTag(@Valid @RequestBody TagRequestDto tagRequestDto){
        return APIResponse.okStatus(tagService.createTag(tagRequestDto));
    }
    @ApiOperation(value = "Tất Cả Tags", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping
    public APIResponse<?> getAllTag(
            @RequestParam(value = "page", defaultValue = APIConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", defaultValue = APIConstants.DEFAULT_PAGE_SIZE) Integer size
    ){
        PaginationDto paginationDto = new PaginationDto(page,size);
        return APIResponse.okStatus(tagService.getAllTags(paginationDto));
    }
    @ApiOperation(value = "Tất Cả Tag Không Phân Trang", response = List.class)
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
        return APIResponse.okStatus(tagService.getAllTagNoPagination());
    }
    @ApiOperation(value = "Update Tag", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PutMapping("/{id}")
    public APIResponse<ApiStatusDto> updateTag(@PathVariable("id") Long id,@Valid @RequestBody TagRequestDto tagRequestDto){
        return APIResponse.okStatus(tagService.updateTag(id,tagRequestDto));
    }
    @ApiOperation(value = "Delete Tags", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping("/deleteMultiple")
    public APIResponse<ApiStatusDto> removeTags(@RequestParam List<Long> ids){
        return APIResponse.okStatus(tagService.deleteTags(ids));
    }
}
