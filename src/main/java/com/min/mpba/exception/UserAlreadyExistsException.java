package com.min.mpba.exception;

/**
 * 사용자 중복 예외 처리
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
