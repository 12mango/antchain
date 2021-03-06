package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.ActivityVO;
import com.example.demo.entity.Activity;

import java.text.ParseException;
import java.util.List;

/**
 * @author 12mango
 * @since 2021/7/16
 */
public interface ActivityService extends IService<Activity> {
    public List<ActivityVO> getAll();
    public ActivityVO getActivity(Integer id);
    public boolean createActivity(ActivityVO data) throws ParseException;
    public double queryTotalMoney();
    public boolean Save(Activity activity);
}
