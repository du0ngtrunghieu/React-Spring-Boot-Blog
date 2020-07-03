package com.trunghieu.api.file;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.CategoryRequestDto;
import com.trunghieu.common.response.APIResponse;
import com.trunghieu.common.response.FileResponse;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 13/6/2020.
 * Class: FileSystemStorageController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/files")
@Api(value = "File APIs")
public class FileSystemStorageController {
    @Autowired
    FileSystemStorageService fileSystemStorageService;

    @ApiOperation(value = "Load all files", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping
    public APIResponse<?> getAllFile(){
        return APIResponse.okStatus(fileSystemStorageService.loadAll().map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(APIConstants.PATH_FILE)
                        .path(path.getFileName().toString())
                        .toUriString())
                .collect(Collectors.toList()));
    }
    @ApiOperation(value = "Upload File", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/upload-file")
    public APIResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file){
//        String name = fileSystemStorageService.store(file);
////
////        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
////                .path(APIConstants.PATH_FILE)
////                .path(name)
////                .toUriString();
        return APIResponse.okStatus(fileSystemStorageService.uploadFile(file));

    }
    @ApiOperation(value = "Get file", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getDetailFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileSystemStorageService.loadAsResource(filename);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "Upload Multiple File", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping(headers = "content-type=multipart/form-data")
    public APIResponse<List<FileResponse>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files){
        return APIResponse.okStatus(fileSystemStorageService.uploadFiles(files));
    }
    @ApiOperation(value = "Delete a File", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @DeleteMapping("/{filename:.+}")
    public APIResponse<?> deleteFile(@PathVariable String filename){
        if(fileSystemStorageService.deleteFile(filename)){
            return APIResponse.errorStatus("Error : Xoá không thành công !", HttpStatus.BAD_REQUEST);
        }else {
            return APIResponse.okStatus(filename);
        }
    }
}
