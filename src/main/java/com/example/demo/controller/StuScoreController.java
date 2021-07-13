package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.common.annotations.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.demo.VO.*;
import com.example.demo.service.*;

import java.util.Collections;
import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/4
 */

@RestController
@RequestMapping("stuScore")
public class StuScoreController {

    @Autowired
    private final StuCouService stuCouService;

    @Autowired
    public StuScoreController(StuCouService stuCouService) {
        this.stuCouService = stuCouService;
    }

    @UserLoginToken
    @GetMapping("")
    public List<StuScoreVO> getAll(@RequestHeader String token,@RequestParam(value="title",required=false) String title){
        String sid = JWT.decode(token).getAudience().get(0);
        if(title==null){
            title="";
        }
        return stuCouService.getAllScores(sid,title);
    }

}
