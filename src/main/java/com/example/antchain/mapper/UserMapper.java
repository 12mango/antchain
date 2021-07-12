package com.example.antchain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.antchain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 12mango
 * @since 2021/7/12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
