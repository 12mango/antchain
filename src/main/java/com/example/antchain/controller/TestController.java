package com.example.antchain.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 12mango
 * @since 2021/7/12
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("")
    public Boolean getStuInfo(){
        return true;
    }
}
