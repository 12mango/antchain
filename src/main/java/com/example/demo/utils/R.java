package com.example.demo.utils;


import com.example.demo.VO.ApiVo;
import com.example.demo.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

/**
 * @author WarmthDawn
 * @since 2021-02-20
 */
public final class R {

    public static <T> ApiVo<T> error(ExceptionCode code, String msg) {
        return new ApiVo<>(code.getCode(), msg, null);
    }

    public static <T> ApiVo<T> error(ExceptionCode code) {
        return new ApiVo<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> ApiVo<T> error() {
        return new ApiVo<>(ExceptionCode.B0001.getCode(), "内部错误", null);
    }

    public static <T> ApiVo<T> ok(T data) {
        return new ApiVo<>(data);
    }

    public static <T> ApiVo<T> ok() {
        return new ApiVo<>();
    }
}
