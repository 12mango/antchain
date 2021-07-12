package com.example.antchain.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author 12mango
 * @since 2021/7/12
 */
@RestController
@RequestMapping("test")
@Api(description = "测试")
public class TestController {

    @ApiOperation(value = "测试", notes = "返回Hello字符串")
    @GetMapping("")
    public String Hello(){
        return "Hello!";
    }
}
