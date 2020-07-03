package com.trunghieu.api.post;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.CategoryRequestDto;
import com.trunghieu.common.dto.PostRequestDto;
import com.trunghieu.common.response.APIResponse;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created on 11/4/2020.
 * Class: PostController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/posts")
@Api(value = "POST APIs")
public class PostController {
    @Autowired
    PostService postService;
    @ApiOperation(value = "Create Post", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping
    public APIResponse<ApiStatusDto> savePost(@Valid @RequestBody PostRequestDto postRequestDto){
        return APIResponse.okStatus(postService.createPost(postRequestDto));
    }
}
