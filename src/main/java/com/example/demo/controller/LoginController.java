package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.VO.ApiVo;
import com.example.demo.VO.LoginVO;
import com.example.demo.common.annotations.PassToken;
import com.example.demo.common.annotations.TeacherOnly;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.exception.AuthException;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.StudentService;
import com.example.demo.security.JwtTokenUtil;

import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@Api(value = "登录相关", tags = {"用于注册登陆的相关接口"})
@RestController
@RequestMapping("login")
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
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService,StudentService studentService,JwtTokenUtil jwtTokenUtil,StudentMapper studentMapper,TeacherService teacherService,TeacherMapper teacherMapper) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
    }

    @ApiOperation("普通用户注册")
    @PostMapping("user/registry")
    public ApiVo<Boolean> userRegistry(@RequestBody LoginVO loginField) throws JsonProcessingException{
        boolean state = userService.userRegistry(loginField);
        if(!state){
            throw new CustomException("用户注册错误", ExceptionCode.A0100);
        }
        return R.ok(state);
    }

    @ApiOperation("普通用户登录")
    @PostMapping("user/login")
    //public String ant_logTry(@RequestBody LoginVO loginField) throws JsonProcessingException {
    public ApiVo<Map<String, String>> userLogin(@RequestBody LoginVO loginField) throws JsonProcessingException {
        Jedis jedis = new Jedis("localhost",6379);
        Integer id = userService.logTry(loginField);
        String token = jwtTokenUtil.getToken(loginField,"user", id);
        jedis.set(token,"user");
        //jedis.expire(token, 1800);
        jedis.close();
        Map<String,String> ret=new HashMap<String,String>();
        ret.put("token",token);
        return R.ok(ret);
    }

    @ApiOperation("管理员登录")
    @PostMapping("admin/login")
    public ApiVo<Map<String, String>> adminLogin(@RequestBody LoginVO loginField) throws JsonProcessingException {
        Jedis jedis = new Jedis("localhost",6379);
        Integer id = userService.adminLogin(loginField);  //这里应该写管理员的登录
        String token = jwtTokenUtil.getToken(loginField,"admin", id);
        jedis.set(token,"admin");
        //jedis.expire(token, 1800);  //有效期半个小时
        jedis.close();
        Map<String,String> ret=new HashMap<String,String>();
        ret.put("token",token);
        return R.ok(ret);
    }

    @ApiOperation("验证普通用户登录态")
    @UserLoginToken
    @GetMapping("user/isLogin")
    public ApiVo<Boolean> userIsLogin(@RequestHeader(defaultValue = "") String token) throws JsonProcessingException{
        if (token.equals("")) {
            throw new CustomException("无token，需要登录", ExceptionCode.A0301); //用户未登录
        }
        Jedis jedis = new Jedis("localhost",6379);

        if(jedis.get(token) == null){
            jedis.close();
            throw new AuthException("token无效或已过期", ExceptionCode.A0220); //登录已过期
        }

        if(!JWT.decode(token).getSubject().equals("user")){
            jedis.close();
            throw new CustomException("无权限查看该页面", ExceptionCode.A0300); //无权限
        }

        return R.ok(true);
    }

    @ApiOperation("验证管理员登录态")
    @TeacherOnly
    @GetMapping("admin/isLogin")
    public ApiVo<Boolean> adminIsLogin(@RequestHeader(defaultValue = "") String token) throws Exception,JsonProcessingException{

        if (token.equals("")) {
            throw new CustomException("无token，需要登录", ExceptionCode.A0301); //用户未登录
        }
        Jedis jedis = new Jedis("localhost",6379);

        if(jedis.get(token) == null){
            jedis.close();
            throw new AuthException("token无效或已过期", ExceptionCode.A0220); //登录已过期
        }

        if(!JWT.decode(token).getSubject().equals("admin")){
            jedis.close();
            throw new CustomException("无权限查看该页面", ExceptionCode.A0300); //无权限
        }

        return R.ok(true);
    }

}
