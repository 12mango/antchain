package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author 12mango
 * @since 2021/5/9
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private String msg;
    private ExceptionCode code;
}
