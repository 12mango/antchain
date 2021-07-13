package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.CourseVO;
import com.example.demo.entity.*;

import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/4
 */
public interface CourseService extends IService<Course> {
    List<CourseVO> getCourses(String sid,String curName,String teaName,Integer full,List<Integer> field,List<Integer> day);
    Boolean chooseCourses(String sid,String cid);
}
