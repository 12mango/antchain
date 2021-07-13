package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * @author 12mango
 * @since 2021/5/9
 */
@Data
//@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class ApiVo<T> {

    private String code = "00000";

    private String msg = "请求成功";

    @JsonInclude
    private T data;

    public ApiVo(T data) {
        this.data = data;
    }

    public ApiVo( String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}