package com.level4.office.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_DIFFERENT_FORMAT(HttpStatus.BAD_REQUEST.value(), "이메일 형식이 올바르지 않습니다."),
    PASSWORD_DIFFERENT_FORMAT(HttpStatus.BAD_REQUEST.value(), "비밀번호는 8~15자리로, 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "이미 등록된 이메일입니다."),
    INVALID_EMAIL_PASSWORD(HttpStatus.BAD_REQUEST.value(), "이메일 또는 비밀번호가 정확하지 않습니다."),
    INVALID_DEPARTMENT(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 부서입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 과목입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "해당 사용자를 찾을 수 없습니다."),
    NOT_FOUND_INSTRUCTOR(HttpStatus.NOT_FOUND.value(), "해당 강사를 찾을 수 없습니다"),
    NOT_FOUND_COURSE(HttpStatus.NOT_FOUND.value(), "해당 강의를 찾을 수 없습니다"),
    PHONENUMBER_ALREADY_EXISTS(HttpStatus.CONTINUE.value(), "이미 등록된 전화번호입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 유효하지 않습니다."),
    NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED.value(), "엑세스 토큰을 찾을 수 없습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "로그인 후 이용해 주세요."),
    OUT_OF_RANGE(HttpStatus.BAD_REQUEST.value(), "요청한 페이지 범위가 적절하지 않습니다."),
    TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED.value(), "이 기능을 사용하기 위해서는 로그인이 필요합니다."),
    ELEMENTS_IS_REQUIRED(HttpStatus.BAD_REQUEST.value(), "필수 입력 필드가 누락되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다."),
    UNEXPECTED_ERROR(443, "예상치 못한 오류가 발생했습니다.");

    private final int httpStatus;
    private final String message;

}