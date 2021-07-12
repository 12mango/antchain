package com.example.antchain.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.antchain.mapper.*;
import com.example.antchain.entity.*;
import com.example.antchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 12mango
 * @since 2021/7/12
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    public boolean Registry(String account,String password) {
        return true;
    }
}