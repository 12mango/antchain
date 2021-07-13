package com.example.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * @author 12mango
 * @since 2021/5/9
 */
public class AuthException extends CustomException {
    public AuthException(String msg, ExceptionCode code) {
        super(msg, code);
    }
}
