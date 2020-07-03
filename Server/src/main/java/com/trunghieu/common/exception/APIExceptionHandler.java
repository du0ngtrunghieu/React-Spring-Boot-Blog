package com.trunghieu.common.exception;

import com.trunghieu.common.aspect.LoggingAspect;
import com.trunghieu.common.response.APIResponseError;
import com.trunghieu.common.util.constant.APIConstants;
import com.trunghieu.common.util.constant.MessageConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 9/4/2020.
 * Class: APIExceptionHandler.java
 * By : Admin
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .listMessage(fieldError.stream()
                        .map(error -> error.getField() + " : " + error.getDefaultMessage())
                        .collect(Collectors.toList()))
                .build();
        return new ResponseEntity(apiResponseError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<APIResponseError> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(e.getMessage())
                .build();
        log.warn(MessageConstants.LOG_LEVEL_WARN + ":" + e.getMessage());
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<APIResponseError> handleBadRequestException(BadRequestException e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(e.getMessage())
                .message(e.getMessage())
                .build();
        log.warn(MessageConstants.LOG_LEVEL_WARN + ":" + e.getMessage());
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class})
    protected ResponseEntity<APIResponseError> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.name())
                .message(e.getMessage())
                .build();
        log.warn(MessageConstants.LOG_LEVEL_WARN + ":" + e.getMessage());
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<APIResponseError> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.name())
                .message(e.getMessage())
                .build();
        log.warn(MessageConstants.LOG_LEVEL_WARN + ":" + e.getMessage());
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseError> handleException(Exception e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(APIConstants.ERROR_UNKOWN)
                .message(!StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : APIConstants.ERROR_UNKNOW_MSG)
                .build();
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIResponseError> handleException(AppException e) {
        log.error(e.getMessage(), e);
        APIResponseError apiResponseError = APIResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(APIConstants.ERROR_UNKOWN)
                .message(!StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : APIConstants.ERROR_UNKNOW_MSG)
                .build();
        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(FileNotFoundException.class)
//    public ResponseEntity<APIResponseError> handleFileException(FileNotFoundException e) {
//        APIResponseError apiResponseError = APIResponseError.builder()
//                .code(HttpStatus.BAD_REQUEST.value())
//                .error(HttpStatus.BAD_REQUEST.name())
//                .message(!StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : APIConstants.ERROR_UNKNOW_MSG)
//                .build();
//        return new ResponseEntity<APIResponseError>(apiResponseError, HttpStatus.BAD_REQUEST);
//    }

}
