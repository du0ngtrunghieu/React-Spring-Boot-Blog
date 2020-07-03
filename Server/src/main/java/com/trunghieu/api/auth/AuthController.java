package com.trunghieu.api.auth;

import com.trunghieu.common.dto.*;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.model.ConfirmationToken;
import com.trunghieu.common.model.User;
import com.trunghieu.common.repository.RoleRepository;
import com.trunghieu.common.repository.UserRepository;
import com.trunghieu.common.response.*;
import com.trunghieu.common.security.JwtTokenProvider;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 9/4/2020.
 * Class: AuthorizationController.java
 * By : Admin
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "User APIs")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AuthService authService;




    @ApiOperation(value = "Đăng Nhập", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/signin")
    public APIResponse<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException | LockedException | BadCredentialsException e) {
            if (e.getClass().equals(BadCredentialsException.class)) {
                APIResponseError apiResponseError = APIResponseError.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(MessageConstants.INVALID_EMAIL_PASSW)
                        .build();
                return APIResponse.errorStatus(apiResponseError, HttpStatus.BAD_REQUEST);

            } else if (e.getClass().equals(DisabledException.class)) {
                return APIResponse.errorStatus(
                        APIConstants.BAD_REQUEST_CODE,
                        MessageConstants.DISABLE,
                        HttpStatus.BAD_REQUEST
                );
            } else if (e.getClass().equals(LockedException.class)) {
                APIResponseError apiResponseError = APIResponseError.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(MessageConstants.LOCKED)
                        .build();
                return APIResponse.errorStatus(apiResponseError, HttpStatus.BAD_REQUEST);
            } else {
                return APIResponse.errorStatus(
                        APIConstants.BAD_REQUEST_CODE,
                        MessageConstants.UNKNOWN,
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        String jwt = tokenProvider.generateToken(authentication);
        return APIResponse.okStatus(new JwtAuthenticationStatusDto(jwt));
    }

    /* Đăng ký cho member */
    @ApiOperation(value = "Đăng Ký", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/signup")
    public APIResponse<ApiStatusDto> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequest) throws BadRequestException {
        return APIResponse.okStatus(authService.registerUser(signUpRequest));
    }
    @ApiOperation(value = "Xác Nhận Tài Khoản", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @GetMapping("confirm-account")
    public APIResponse<?> getMethodName(@RequestParam("token") String token) {
        ConfirmationToken confirmationToken = authService.findByConfirmationToken(token);
        if (confirmationToken == null) {
            throw new BadRequestException("Invalid token");
        }
        User user = confirmationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((confirmationToken.getExpiryDate().getTime() -
                calendar.getTime().getTime()) <= 0) {
            return APIResponse.errorStatus("Link expired. Generate new link from http://localhost:3000/login", HttpStatus.BAD_REQUEST);
        }
        user.setVerified(true);
        authService.save(user);
        return APIResponse.okStatus(new ApiStatusDto(true,"Account verified successfully!",null));
    }

    @ApiOperation(value = "Send Email", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/send-email")
    public APIResponse<?> sendVerificationMail(@Valid @RequestBody
                                                          VerifyEmailRequestDto emailRequest) {
        return APIResponse.okStatus(authService.sendVerificationEmail(emailRequest));
    }

    @ApiOperation(value = "Reset Password", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = APIConstants.SUCCESS_CODE, message = MessageConstants.SUCCESS),
            @ApiResponse(code = APIConstants.UNAUTHORIZED_CODE, message = MessageConstants.NOT_AUTHENTICATED),
            @ApiResponse(code = APIConstants.FORBIDDEN_CODE, message = MessageConstants.ACCESS_DENIED),
            @ApiResponse(code = APIConstants.NOT_FOUND_CODE, message = MessageConstants.NOT_FOUND),
            @ApiResponse(code = APIConstants.BAD_REQUEST_CODE, message = MessageConstants.BAD_REQUEST),
            @ApiResponse(code = APIConstants.INTERNAL_SERVER_ERROR_CODE, message = MessageConstants.SERVER_ERROR)
    })
    @PostMapping("/reset-password")
    public APIResponse<ApiStatusDto> resetPassword(@Valid @RequestBody ResetPasswordRequestDto emailRequest) {
        return APIResponse.okStatus(authService.resetPassword(emailRequest));
    }


}
