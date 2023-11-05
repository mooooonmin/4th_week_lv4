package com.level4.office.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorMessage errorMessage = ErrorMessage.USER_NOT_FOUND; // 예외 유형에 맞는 ErrorMessage 지정
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }
}
