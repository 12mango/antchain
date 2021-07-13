package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.CourseVO;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.VO.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.example.demo.service.*;
import com.example.demo.mapper.*;
import com.example.demo.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@Service("CourseService")
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private CourseMapper courseMapper;
    private StuCouMapper stuCouMapper;
    private StuCouService stuCouService;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper,StuCouMapper stuCouMapper,StuCouService stuCouService){
        this.courseMapper = courseMapper;
        this.stuCouMapper = stuCouMapper;
        this.stuCouService = stuCouService;
    }

    public List<CourseVO> getCourses(String sid,String curName,String teaName,Integer full,List<Integer> field,List<Integer> day){

        List<String> Choose = stuCouMapper.selectList(Wrappers.<StuCou>lambdaQuery().eq(StuCou::getSid, sid))
                                .stream().map(StuCou::getCid).collect(Collectors.toList());

        System.out.println(Choose);

        Map<Integer, String> COU = new HashMap<Integer,String>();
        COU.put(1, "星期一");
        COU.put(2, "星期二");
        COU.put(3, "星期三");
        COU.put(4, "星期四");
        COU.put(5, "星期五");
        COU.put(6, "星期六");
        COU.put(7, "星期日");
        Map<Integer, String> FIELD = new HashMap<Integer,String>();
        FIELD.put(1, "社会科学与现代社会");
        FIELD.put(2, "科学精神与生命关怀");
        FIELD.put(3, "中华文化与世界文明");
        FIELD.put(4, "艺术体验与审美鉴赏");

        List<Course> result = new ArrayList<Course>();
        if(full==0){
            result = courseMapper.selectList(Wrappers.<Course>lambdaQuery().like(Course::getCurname, curName).like(Course::getTeaname, teaName).ne(Course::getNowremain,0));
        }
        else{
            result = courseMapper.selectList(Wrappers.<Course>lambdaQuery().like(Course::getCurname, curName).like(Course::getTeaname, teaName));
        }
        List<CourseVO> ret = new ArrayList<CourseVO>();
        result.forEach((item)->{
            CourseVO tmp = new CourseVO();
            if(day.contains(item.getDay())&&field.contains(item.getField())) {
                //if(true){
                tmp.setId(item.getId());
                tmp.setCredit(item.getCredit());
                tmp.setTeaname(item.getTeaname());
                tmp.setCurname(item.getCurname());
                tmp.setNowremain(item.getNowremain());
                tmp.setMaxremain(item.getMaxremain());
                tmp.setField(FIELD.get(item.getField()));
                tmp.setDay(COU.get(item.getDay()));
                tmp.setStartweek(item.getStartweek());
                tmp.setEndweek(item.getEndweek());
                tmp.setWeeklytimes(item.getWeeklytimes());
                tmp.setStartsection(item.getStartsection());
                tmp.setEndsection(item.getEndsection());
                tmp.setChoose(Choose.contains(item.getId().toString()));
                ret.add(tmp);
            }
        });
        return ret;
    }

    public Boolean chooseCourses(String sid,String cid){
        synchronized (this) {  //悲观锁 spring单例
            Integer Cid = Integer.parseInt(cid);
            Course course = courseMapper.selectById(Cid); //courseMapper.selectOne(Wrappers.<Course>lambdaQuery().e)
            if (course.getNowremain() == 0) {
                return false;
            }
            course.setNowremain(course.getNowremain() - 1);
            updateById(course);
            stuCouService.createChooseInfo(sid, cid);
            return true;
        }
    }
}
