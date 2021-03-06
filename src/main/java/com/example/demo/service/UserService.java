package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.LoginVO;
import com.example.demo.entity.User;

/**
 * @author 12mango
 * @since 2021/7/16
 */
public interface UserService extends IService<User> {
    public Integer logTry(LoginVO loginField);
    public boolean userRegistry(LoginVO loginField);
    public Integer adminLogin(LoginVO loginField);
}
