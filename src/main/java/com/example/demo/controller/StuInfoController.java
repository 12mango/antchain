package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.entity.StuCou;
import com.example.demo.exception.AuthException;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StuCouService;
import com.example.demo.service.StudentService;
import com.example.demo.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 12mango
 * @since 2021/4/5
 */

@RestController
@RequestMapping("stuInfo")
public class StuInfoController {

    @Autowired
    private StudentService studentService;

    @Autowired
    public StuInfoController(StudentService studentService) {
        this.studentService = studentService;
    }

    @UserLoginToken
    @GetMapping("")
    //public StudentVO getStuInfo(@RequestHeader String token, @RequestParam("sid") String sid){
    public StudentVO getStuInfo(@RequestHeader String token){

        String sid = JWT.decode(token).getAudience().get(0);
        return studentService.getStuInfo(sid);
    }

    @UserLoginToken
    @GetMapping("/sendCode")
    public boolean sendCode(@RequestHeader String token,@RequestParam String email){
        String sid = JWT.decode(token).getAudience().get(0);
        return studentService.sendCode(sid,email);
    }

    @UserLoginToken
    @GetMapping("/updateEmail")
    public boolean updateEmail(@RequestHeader String token,@RequestParam String email,@RequestParam String code){
        String sid = JWT.decode(token).getAudience().get(0);
        return studentService.verifyCode(sid,email,code);
    }

    //public List<StudentVO> getStuInfo(@RequestParam("sid") String sid){
    //    List<StudentVO>courses = new ArrayList<StudentVO>();
    //    courses.add(studentService.getStuInfo(sid));
    //    courses.add(studentService.getStuInfo(sid));
    //    return courses;
    //}
}
