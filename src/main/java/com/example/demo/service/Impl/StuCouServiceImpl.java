package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.StuScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.example.demo.service.*;
import com.example.demo.mapper.*;
import com.example.demo.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@Service("StuCouService")
public class StuCouServiceImpl extends ServiceImpl<StuCouMapper, StuCou> implements StuCouService{

    private StuCouMapper stuCouMapper;
    private CourseMapper courseMapper;

    @Autowired
    public StuCouServiceImpl(StuCouMapper stuCouMapper,CourseMapper courseMapper){
        this.stuCouMapper=stuCouMapper;
        this.courseMapper=courseMapper;
    }

    public List<StuScoreVO> getAllScores(String sid,String title){
        List<StuCou> result = stuCouMapper.selectList(Wrappers.<StuCou>lambdaQuery().eq(StuCou::getSid, sid));
        List<StuScoreVO> ret = new ArrayList<StuScoreVO>();
        result.forEach((item)->{
            StuScoreVO tmp = new StuScoreVO();
            Course cou =courseMapper.selectOne(Wrappers.<Course>lambdaQuery().eq(Course::getId, item.getCid()).like(Course::getCurname,title));
            if(cou != null) {
                tmp.setCredit(cou.getCredit());
                tmp.setTeaName(cou.getTeaname());
                tmp.setCurName(cou.getCurname());
                tmp.setScore(item.getTotal());
                ret.add(tmp);
            }
        });
        return ret;
    }

    public Boolean createChooseInfo(String sid,String cid){
        StuCou stuCou = new StuCou();
        stuCou.setSid(sid);
        stuCou.setCid(cid);
        save(stuCou);
        return true;
    }

}
