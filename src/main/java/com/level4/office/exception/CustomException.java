package com.level4.office.exception;


// TODO 나중에는 인터페이스 구현으로 방식 바꾸기
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    // 사용자를 찾을 수 없는 경우
    public static class UserNotFoundException extends CustomException {
        public UserNotFoundException() {
            super(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    // 비밀번호가 일치하지 않는 경우
    public static class InvalidPasswordException extends CustomException {
        public InvalidPasswordException() {
            super(ErrorMessage.InvalidPasswordException.getMessage());
        }
    }
}