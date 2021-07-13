package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.CourseVO;
import com.example.demo.VO.LoginVO;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 12mango
 * @since 2021/5/9
 */
@Service("TeacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    private TeacherMapper teacherMapper;
    private TeaCouMapper teaCouMapper;
    private CourseMapper courseMapper;
    private StuCouMapper stuCouMapper;
    private StudentMapper studentMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper,TeaCouMapper teaCouMapper,CourseMapper courseMapper,StuCouMapper stuCouMapper,StudentMapper studentMapper){
        this.teacherMapper = teacherMapper;
        this.teaCouMapper = teaCouMapper;
        this.courseMapper = courseMapper;
        this.stuCouMapper = stuCouMapper;
        this.studentMapper = studentMapper;
    }

    public String logTry(LoginVO loginField){
        Teacher tmp = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTid, loginField.getLoginUsername()).eq(Teacher::getPassword,loginField.getLoginPassword()));
        if(tmp==null){
            return "error";
        }
        else{
            return "ok";
        }
    }

    public List<CourseVO> getCourses(String tid){

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

        List<String> CID = teaCouMapper.selectList(Wrappers.<TeaCou>lambdaQuery().eq(TeaCou::getTid,tid)).stream().map(TeaCou::getCid).collect(Collectors.toList());
        List<CourseVO> ret = new ArrayList<CourseVO>();
        CID.forEach((item)->{
            Integer id = Integer.parseInt(item);
            Course cou = courseMapper.selectById(id);
            CourseVO tmp = new CourseVO();

            tmp.setId(cou.getId());
            tmp.setCredit(cou.getCredit());
            tmp.setTeaname(cou.getTeaname());
            tmp.setCurname(cou.getCurname());
            tmp.setNowremain(cou.getNowremain());
            tmp.setMaxremain(cou.getMaxremain());
            tmp.setField(FIELD.get(cou.getField()));
            tmp.setDay(COU.get(cou.getDay()));
            tmp.setStartweek(cou.getStartweek());
            tmp.setEndweek(cou.getEndweek());
            tmp.setWeeklytimes(cou.getWeeklytimes());
            tmp.setStartsection(cou.getStartsection());
            tmp.setEndsection(cou.getEndsection());
            //tmp.setChoose(cou.contains(cou.getId().toString()));
            ret.add(tmp);
        });
        return ret;
    }

    public List<StuScoreVO> getScores(String tid, String cid){
        //CID = Integer.parseInt(item);
        List<StuCou> stuCou = stuCouMapper.selectList(Wrappers.<StuCou>lambdaQuery().eq(StuCou::getCid,cid));
        stuCou.sort((o1, o2)->o1.getSid().compareTo(o2.getSid()));
        List<StuScoreVO> ret = new ArrayList<StuScoreVO>();

        stuCou.forEach((item)->{
            StuScoreVO tmp = new StuScoreVO();
            String name = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getSid,item.getSid())).getName();
            tmp.setSid(item.getSid());
            tmp.setCid(item.getCid());
            tmp.setName(name);
            tmp.setTask1(item.getTask1());
            tmp.setTask2(item.getTask2());
            tmp.setTask3(item.getTask3());
            tmp.setFinalscore(item.getFinalscore());
            tmp.setScore(item.getTotal());
            ret.add(tmp);
        });
        return ret;
    }

    public Boolean summitScores(List<StuScoreVO> lst){
        lst.forEach((item)->{
            UpdateWrapper<StuCou> wrapper = new UpdateWrapper<StuCou>();
            stuCouMapper.update(null,wrapper.eq("sid",item.getSid()).eq("cid",item.getCid())
                    .set("task1",item.getTask1()).set("task2",item.getTask2()).set("task3",item.getTask3())
                    .set("finalscore",item.getFinalscore()).set("total",item.getScore()));
        });
        return true;
    }

}
