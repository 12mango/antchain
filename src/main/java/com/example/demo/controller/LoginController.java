package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.VO.LoginVO;
import com.example.demo.common.annotations.PassToken;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentService;
import com.example.demo.security.JwtTokenUtil;

import com.example.demo.service.TeacherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@RestController
@RequestMapping("login")
@PassToken
public class LoginController {
    @Autowired
    private final StudentService studentService;

    @Autowired
    private final TeacherService teacherService;

    @Autowired
    private final StudentMapper studentMapper;

    @Autowired
    private final TeacherMapper teacherMapper;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(StudentService studentService,JwtTokenUtil jwtTokenUtil,StudentMapper studentMapper,TeacherService teacherService,TeacherMapper teacherMapper) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    @PostMapping("")
    public String logTry(@RequestBody LoginVO loginField) throws JsonProcessingException {
        Jedis jedis = new Jedis("localhost",6379);
        Map<String, String> map = new HashMap<String, String>();
        //map.put(loginField.get)
        String stateStudent = studentService.logTry(loginField);
        String stateTeacher = teacherService.logTry(loginField);
        if(stateStudent == "ok"){
            String token = jwtTokenUtil.getToken(loginField,"student");
            String sid = JWT.decode(token).getAudience().get(0);
            Student user = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getSid, sid));
            map.put("state","student");
            map.put("token",token);
            map.put("name",user.getName());
            jedis.set(token,"student");
            jedis.expire(token, 1800);
        }
        else if(stateTeacher == "ok"){
            String token = jwtTokenUtil.getToken(loginField,"teacher");
            String sid = JWT.decode(token).getAudience().get(0);
            Teacher user = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTid, sid));
            map.put("state","teacher");
            map.put("token",token);
            map.put("name",user.getName());
            jedis.set(token,"teacher");
            jedis.expire(token, 1800);
        }
        else{
            map.put("state", "error");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        jedis.close();
        return json;
    }
}
