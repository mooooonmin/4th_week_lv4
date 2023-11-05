package com.level4.office.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다"),
    INVALID_INPUT("데이터를 잘못 입력하셨습니다"),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
