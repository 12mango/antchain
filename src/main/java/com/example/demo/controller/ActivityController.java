package com.example.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Api(value = "活动相关", tags = {"用于活动信息的相关接口"})
@RestController
@RequestMapping("activity")
public class ActivityController {

}
