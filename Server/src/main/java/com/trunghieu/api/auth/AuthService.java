package com.trunghieu.api.auth;

import com.trunghieu.common.dto.*;
import com.trunghieu.common.exception.AppException;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.ConfirmationToken;
import com.trunghieu.common.model.Role;
import com.trunghieu.common.model.User;
import com.trunghieu.common.repository.*;
import com.trunghieu.common.security.CustomUserDetailsService;
import com.trunghieu.common.util.BlogENUM;
import com.trunghieu.common.util.constant.MessageConstants;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Created on 9/4/2020.
 * Class: AuthorizationService.java
 * By : Admin
 */
@Service
public class AuthService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    AuthService authService;
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    OtpGenerator otpGenerator;

    public ApiStatusDto registerUser(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new BadRequestException(MessageConstants.EMAIL_USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BadRequestException(MessageConstants.EMAIL_USERNAME_ALREADY_EXISTS);
        }
        User user = new User(signUpRequestDto.getName(), signUpRequestDto.getUsername(),
                signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        Role userRole = roleRepository.findByName(BlogENUM.RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));
//
        User result = userRepository.save(user);
        ConfirmationToken confirmationToken = authService.createToken(user);
        emailSenderService.sendMail(user.getEmail(), confirmationToken.getConfirmationToken());
        return new ApiStatusDto(true, MessageConstants.USER_REGISTER_SUCCESS,result);
    }
    public boolean ChangePassword(String emailOrUserName ,String password){
        Optional<User> user =  userRepository.findByUsernameOrEmail(emailOrUserName,emailOrUserName);
        if(user.isPresent()){
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }


    /* TOKEN */
    public ConfirmationToken createToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        return confirmationTokenRepository.save(confirmationToken);
    }
    public ConfirmationToken findByConfirmationToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }
    public void deleteToken(ConfirmationToken confirmationToken) {
        this.confirmationTokenRepository.delete(confirmationToken);
    }

    public void save(User user){
         userRepository.save(user);
    }
    public ApiStatusDto sendVerificationEmail(VerifyEmailRequestDto emailRequest){
        if(userRepository.existsByEmail(emailRequest.getEmail())){
            if(userDetailsService.isAccountVerified(emailRequest.getEmail())){
                throw new BadRequestException("Email is already verified");
            } else {
                User user = userRepository.findByEmail(emailRequest.getEmail()).orElseThrow(
                        () -> new ResourceNotFoundException("User","Email",emailRequest.getEmail())
                );
                ConfirmationToken token = authService.createToken(user);
                emailSenderService.sendMail(user.getEmail(), token.getConfirmationToken());
                return new ApiStatusDto(true,"Verification link is sent on your mail id",null);
            }
        } else {
            throw new BadRequestException("Email is not associated with any account");
        }
    }
    public ApiStatusDto resetPassword(ResetPasswordRequestDto loginRequest){
        if(userRepository.existsByEmail(loginRequest.getEmail())){
            int opt = otpGenerator.getOtp(loginRequest.getEmail());
            if(opt == loginRequest.getOtpNo()){
                if(this.ChangePassword(loginRequest.getEmail(), loginRequest.getPassword())) {
                    return new ApiStatusDto(true,"Password changed successfully",false);
                } else {
                    throw new BadRequestException("Unable to change password. Try again!");
                }
            }else {
                throw new BadRequestException("Mã OPT không chính xác vui lòng nhập lại !");
            }

        } else {
            throw new BadRequestException("User not found with this email id");
        }
    }


}
