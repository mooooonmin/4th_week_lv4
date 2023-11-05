package com.level4.office.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorMessage errorMessage = ErrorMessage.INVALID_INPUT;
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorMessage errorMessage = ErrorMessage.USER_NOT_FOUND;
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // USER_NOT_FOUND 사용자 못찾음
    @ExceptionHandler(CustomException.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException.UserNotFoundException e) {
        ErrorMessage errorMessage = ErrorMessage.USER_NOT_FOUND;
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // InvalidPasswordException 비밀번호 불일치
    @ExceptionHandler(CustomException.InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(CustomException.InvalidPasswordException e) {
        ErrorMessage errorMessage = ErrorMessage.InvalidPasswordException;
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // AuthenticationException 인증실패
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorMessage errorMessage = ErrorMessage.UNAUTHORIZED;
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // 권한이 없을 때 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorMessage errorMessage = ErrorMessage.FORBIDDEN;
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }
}
