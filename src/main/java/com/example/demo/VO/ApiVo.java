package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * @author 12mango
 * @since 2021/5/9
 */

@ApiModel(value = "response对象",description = "后端API统一返回该格式的对象")
@Data
//@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class ApiVo<T> {

    @ApiModelProperty("自定义状态码")
    private String code = "00000";

    @ApiModelProperty("相关信息")
    private String msg = "请求成功";

    @ApiModelProperty("返回数据，报错时一般返回null")
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