package com.trunghieu.api.user;

import com.trunghieu.common.dto.InfoUserRequestDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.response.APIResponse;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Created on 12/6/2020.
 * Class: UserController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/users")
@Api(value = "User APIs")
public class UserController {
    @Autowired
    UserService userService;
    @ApiOperation(value = "Tất Cả User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping
    public APIResponse<?> getAllUser(
            @RequestParam(value = "page", defaultValue = APIConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", defaultValue = APIConstants.DEFAULT_PAGE_SIZE) Integer size
    ){
        PaginationDto paginationDto = new PaginationDto(page,size);
        return APIResponse.okStatus(userService.getAll(paginationDto));
    }
    @ApiOperation(value = "Get Detail User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("/{userNameOrEmail}")
    public APIResponse<?> getDetailUser(
            @PathVariable("userNameOrEmail") String userNameOrEmail
    ){
        return APIResponse.okStatus(userService.getDetailUser(userNameOrEmail));
    }
    @ApiOperation(value = "Delete User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping("/{userNameOrEmail}")
    public APIResponse<?> deleteUser(
            @PathVariable("userNameOrEmail") String userNameOrEmail
    ){
        return APIResponse.okStatus(userService.deleteUser(userNameOrEmail));
    }
    @ApiOperation(value = "Delete User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping
    public APIResponse<?> deleteUsers(
            @RequestParam List<String> userNameOrEmail
    ){
        return APIResponse.okStatus(userService.deleteUsers(userNameOrEmail));
    }
    @ApiOperation(value = "Delete Multiple User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping("/delete-multiple-user")
    public APIResponse<?> deleteMultipleUser(
            @RequestParam List<Long> ids
    ){
        return APIResponse.okStatus(userService.deleteUserByIds(ids));
    }
    @ApiOperation(value = "Update User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{userNameOrEmail}"
    )
    public APIResponse<?> updateUser(
            @PathVariable("userNameOrEmail") String userNameOrEmail,final InfoUserRequestDto infoUserRequestDto,@RequestParam(value = "file") MultipartFile afile
            ){
        return APIResponse.okStatus(userService.updateUser(afile,userNameOrEmail,infoUserRequestDto));
    }
    @ApiOperation(value = "SEARCH USER - Tìm Kiếm USER", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/search")
    public APIResponse<?> searchPermission(@RequestParam("words") String search){
        return APIResponse.okStatus(userService.searchUser(search));
    }
    @ApiOperation(value = "SEARCH USER - V2", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/s")
    public APIResponse<?> searchPermissionNotIn(@RequestParam("words") String search,@RequestParam("ids") List<Long> ids){
        return APIResponse.okStatus(userService.searchUserNotIn(search,ids));
    }
    @ApiOperation(value = "Update Role For User", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PutMapping("/userhasrole/{roleId}/{userId}")
    public APIResponse<?> searchPermissionNotIn(@PathVariable("roleId") Long roleId,@PathVariable("userId") Long userId){
        return APIResponse.okStatus(userService.addUserToRole(roleId,userId));
    }

}
