package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.ActivityVO;
import com.example.demo.entity.Activity;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.ActivityMapper;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Service("ActivityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private ActivityMapper activityMapper;

    @Autowired
    public ActivityServiceImpl(ActivityMapper activityMapper){
        this.activityMapper=activityMapper;
    }

    public String DateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(date);
        return str;
    }

    public Date StringToDate(String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(str);
        return date;
    }

    public List<ActivityVO> getAll(){
        List<Activity> result = activityMapper.selectList(Wrappers.<Activity>lambdaQuery());
        List<ActivityVO> ret = new ArrayList<ActivityVO>();
        result.forEach((item)->{
            ActivityVO tmp = new ActivityVO();
            tmp.setId(item.getId());
            tmp.setTarget(item.getTarget());
            tmp.setNow(item.getNow());
            tmp.setStart(DateToString(item.getStart()));
            tmp.setEnd(DateToString(item.getEnd()));
            tmp.setTopic(item.getTopic());
            tmp.setDirector(item.getDirector());
            tmp.setContact(item.getContact());
            tmp.setDescription(item.getDescription());
            tmp.setUrl(item.getUrl());
            ret.add(tmp);
        });
        return ret;
    }

    public ActivityVO getActivity(Integer id){
        Activity result = activityMapper.selectById(id);
        if(result == null){
            throw new CustomException("活动不存在", ExceptionCode.C0302);
        }
        ActivityVO ret = new ActivityVO();
        ret.setId(result.getId());
        ret.setTarget(result.getTarget());
        ret.setNow(result.getNow());
        ret.setStart(DateToString(result.getStart()));
        ret.setEnd(DateToString(result.getEnd()));
        ret.setTopic(result.getTopic());
        ret.setDirector(result.getDirector());
        ret.setContact(result.getContact());
        ret.setDescription(result.getDescription());
        ret.setUrl(result.getUrl());
        return ret;
    }

    public boolean createActivity(ActivityVO data) throws ParseException {
        Activity activity = new Activity();
        activity.setTarget(data.getTarget());
        activity.setNow(data.getNow());
        activity.setStart(StringToDate(data.getStart()));
        activity.setEnd(StringToDate(data.getEnd()));
        activity.setTopic(data.getTopic());
        activity.setDirector(data.getDirector());
        activity.setContact(data.getContact());
        activity.setDescription(data.getDescription());
        activity.setUrl(data.getUrl());
        save(activity);
        return true;
    }

    public double queryTotalMoney(){
        double ret = 0.0;
        ret = activityMapper.selectList(Wrappers.<Activity>lambdaQuery()).stream().mapToDouble(Activity::getNow).sum();
        return ret;
    }
}
