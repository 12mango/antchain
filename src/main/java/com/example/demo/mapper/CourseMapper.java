package com.example.demo.mapper;

import com.example.demo.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course>{

}
