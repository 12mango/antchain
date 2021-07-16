package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}
