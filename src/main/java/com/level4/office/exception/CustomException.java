package com.level4.office.exception;


// TODO 나중에는 인터페이스 구현으로 방식 바꾸기
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    // 사용자 없음
    public static class UserNotFoundException extends CustomException {
        public UserNotFoundException() {
            super(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    // 비밀번호 불일치
    public static class InvalidPasswordException extends CustomException {
        public InvalidPasswordException() {
            super(ErrorMessage.InvalidPasswordException.getMessage());
        }
    }

    // 이미 존재하는 데이터
    public static class AlreadyExistsException extends CustomException {
        public AlreadyExistsException() {
            super(ErrorMessage.ALREADY_EXISTS.getMessage());
        }
    }

    // 유효하지 않은 토큰
    public static class InvalidTokenException extends CustomException {
        public InvalidTokenException() {
            super(ErrorMessage.INVALID_TOKEN.getMessage());
        }
    }

    // 토큰이 만료된 경우
    public static class TokenExpiredException extends CustomException {
        public TokenExpiredException() {
            super(ErrorMessage.TOKEN_EXPIRED.getMessage());
        }
    }
}