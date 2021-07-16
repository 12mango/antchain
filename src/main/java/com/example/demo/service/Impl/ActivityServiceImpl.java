package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.CourseVO;
import com.example.demo.entity.Activity;
import com.example.demo.mapper.ActivityMapper;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String str = new String();
        return str;
    }

    public Date StringToDate(String str){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
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
            tmp.setStart(item.getStart());
            tmp.setEnd(item.getEnd());
            tmp.setTopic(item.getTopic());
            ret.add(tmp);
        });
        return ret;
    }

    public ActivityVO getActivity(Integer id){
        Activity result = activityMapper.selectById(id);
        ActivityVO ret = new ActivityVO();
        ret.setId(result.getId());
        ret.setTarget(result.getTarget());
        ret.setNow(result.getNow());
        ret.setStart(result.getStart());
        ret.setEnd(result.getEnd());
        ret.setTopic(result.getTopic());
        return ret;
    }

    public boolean createActivity(ActivityVO data){
        Activity activity = new Activity();
        activity.setTarget(data.getTarget());
        activity.setNow(data.getNow());
        activity.setStart(data.getStart());
        activity.setEnd(data.getEnd());
        activity.setTopic(data.getTopic());
        save(activity);
        return true;
    }

    public double queryTotalMoney(){
        double ret = 0.0;
        ret = activityMapper.selectList(Wrappers.<Activity>lambdaQuery()).stream().mapToDouble(Activity::getNow).sum();
        return ret;
    }
}
