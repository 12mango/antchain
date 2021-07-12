package com.example.antchain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.antchain.entity.User;

/**
 * @author 12mango
 * @since 2021/7/12
 */
public interface UserService extends IService<User> {
    boolean Registry(String account,String password);
}
