package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.VO.CourseVO;
import com.example.demo.VO.StudentVO;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.service.CourseService;
import com.example.demo.service.StuCouService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/5
 */

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final StuCouService stuCouService;

    @Autowired
    public CourseController(CourseService courseService,StuCouService stuCouService) {
        this.courseService = courseService;
        this.stuCouService = stuCouService;
    }

    @GetMapping("")
    @UserLoginToken
    public List<CourseVO> getCourses(@RequestHeader String token,
                                    @RequestParam(value="curName",required=false,defaultValue="") String curName,
                                     @RequestParam(value="teaName",required=false,defaultValue="") String teaName,
                                     @RequestParam(value="full",required=false) Integer full,
                                     @RequestParam(value="field",required=false) List<Integer> field,
                                     @RequestParam(value="day",required=false) List<Integer> day){
        /*
        if(curName == null){
            curName = "";
        }
        if(teaName == null){
            teaName = "";
        }
        if(field == null){
            field = new ArrayList<Integer>;
        }
         */
        String sid = JWT.decode(token).getAudience().get(0);
        return courseService.getCourses(sid,curName,teaName,full,field,day);
    }

    @GetMapping("/choose")
    @UserLoginToken
    public boolean chooseCourses(@RequestHeader String token,@RequestParam String cid){
        String sid = JWT.decode(token).getAudience().get(0);
        return courseService.chooseCourses(sid,cid);
    }
}