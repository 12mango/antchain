package com.example.demo.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.LoginVO;
import com.example.demo.VO.StudentVO;
import com.example.demo.entity.Course;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.*;

/**
 * @author 12mango
 * @since 2021/4/5
 */
public interface StudentService extends IService<Student> {
    StudentVO getStuInfo(String sid);
    String logTry(LoginVO loginField);
    boolean sendCode(String sid,String email);
    boolean verifyCode(String sid,String email,String code);
}