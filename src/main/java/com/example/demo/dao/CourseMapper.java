package com.example.demo.dao;

import com.example.demo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface CourseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbggenerated
     */
    int insert(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbggenerated
     */
    List<Course> selectAll();
}