package com.trunghieu.api.auth;

import com.trunghieu.common.dto.VerifyEmailRequestDto;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.response.APIResponse;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created on 15/6/2020.
 * Class: OtpController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "User APIs")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthService authService;
    @ApiOperation(value = "Generate Otp", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/generate-otp")
    public APIResponse<?> generateOtp(@Valid @RequestBody
                                                 VerifyEmailRequestDto emailRequest) {

        if(authService.existsByEmail(emailRequest.getEmail())) {
            if(otpService.generateOtp(emailRequest.getEmail())) {
                return APIResponse.okStatus("Otp sent on email account");
            } else {
                throw new BadRequestException("Unable to send OTP. try again");
            }
        } else {
            throw new BadRequestException("Email is not associated with any account.");
        }
    }
    @ApiOperation(value = "Validate Opt", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/validate-otp")
    public APIResponse<?> validateOtp(@Valid @RequestBody VerifyEmailRequestDto emailRequest) {
        if(emailRequest.getOtpNo() != null) {
            if(otpService.validateOTP(emailRequest.getEmail(), emailRequest.getOtpNo())) {
                return APIResponse.okStatus("OTP verified successfully");

            }
        }
        return APIResponse.errorStatus("Invalid OTP", HttpStatus.BAD_REQUEST);
    }
}
