package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.VO.CourseVO;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.common.annotations.TeacherOnly;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 12mango
 * @since 2021/5/9
 */
@RestController
@RequestMapping("setScore")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @TeacherOnly
    @GetMapping("getCourses")
    public List<CourseVO> getCourses(@RequestHeader String token)
    {
        String tid = JWT.decode(token).getAudience().get(0);
        return teacherService.getCourses(tid);
    }

    @TeacherOnly
    @GetMapping("getScores")
    public List<StuScoreVO> getScores(@RequestHeader String token, @RequestParam String cid)
    {
        String tid = JWT.decode(token).getAudience().get(0);
        return teacherService.getScores(tid,cid);
    }

    @TeacherOnly
    @PostMapping("summitScores")
    public Boolean summitScores(@RequestHeader String token, @RequestBody List<StuScoreVO> data)
    {
        System.out.println(data);
        String tid = JWT.decode(token).getAudience().get(0);
        return teacherService.summitScores(data);
    }

}
