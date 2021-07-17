package com.example.demo.controller;

import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.ApiVo;
import com.example.demo.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.R;

import java.text.ParseException;
import java.util.List;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Api(tags = {"活动信息相关"})
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService){
        this.activityService=activityService;
    }

    @ApiOperation("获取所有活动信息")
    @GetMapping("getAllInfo")
    public ApiVo<List<ActivityVO>> getAll(){
        return R.ok(activityService.getAll());
    }

    @ApiOperation("根据ID获取活动信息")
    @GetMapping("getInfo")
    public ApiVo<ActivityVO> getActivity(@RequestParam Integer id){
        return R.ok(activityService.getActivity(id));
    }

    @ApiOperation("添加活动")
    @PostMapping("add")
    public ApiVo<Boolean> createActivity(@RequestBody ActivityVO data) throws ParseException {
        return R.ok(activityService.createActivity(data));
    }

    @ApiOperation("获取总募集金额")
    @GetMapping("getTotalMoney")
    public ApiVo<Double> queryTotalMoney(){
        return R.ok(activityService.queryTotalMoney());
    }

}
