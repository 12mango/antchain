package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.LoginVO;
import com.example.demo.entity.Administrator;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.AdministratorMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 12mango
 * @since 2021/7/12
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    private AdministratorMapper administratorMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,AdministratorMapper administratorMapper) {
        this.userMapper = userMapper;
        this.administratorMapper = administratorMapper;
    }

    public boolean userRegistry(LoginVO loginField){

        //用户已存在
        User tmp = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, loginField.getLoginUsername()));
        if(tmp!=null){
            return false;
        }

        User user=new User();
        user.setAccount(loginField.getLoginUsername());
        user.setPassword(loginField.getLoginPassword());
        user.setMoney(0.0);
        user.setScore(0.0);
        save(user);

        return true;
    }

    public Integer logTry(LoginVO loginField){
        User tmp = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, loginField.getLoginUsername()).eq(User::getPassword,loginField.getLoginPassword()));

        if(tmp==null) {
            throw new CustomException("用户名或密码错误", ExceptionCode.A0201);
        }
        return tmp.getId();
    }

    public Integer adminLogin(LoginVO loginField){
        Administrator tmp = administratorMapper.selectOne(Wrappers.<Administrator>lambdaQuery().eq(Administrator::getAccount, loginField.getLoginUsername()).eq(Administrator::getPassword,loginField.getLoginPassword()));
        if(tmp==null) {
            throw new CustomException("用户名或密码错误", ExceptionCode.A0201);
        }
        return tmp.getId();
    }

}
