package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
}
