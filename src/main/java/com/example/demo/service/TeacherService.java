package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.CourseVO;
import com.example.demo.VO.LoginVO;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;

import java.util.List;

/**
 * @author 12mango
 * @since 2021/5/9
 */
public interface TeacherService extends IService<Teacher> {
    String logTry(LoginVO loginField);
    List<CourseVO> getCourses(String tid);
    List<StuScoreVO> getScores(String tid, String cid);
    Boolean summitScores(List<StuScoreVO> lst);
}
