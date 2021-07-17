package com.example.demo.controller;

import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.ApiVo;
import com.example.demo.VO.FlowVO;
import com.example.demo.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.R;

import java.text.ParseException;
import java.util.List;

@Api(value = "活动相关", tags = {"用于资金流向的相关接口"})
@RestController
@RequestMapping("flow")
public class FlowController {
    @Autowired
    private final FlowService flowService;

    @Autowired
    public FlowController(FlowService flowService){
        this.flowService=flowService;
    }

    /*
    @ApiOperation("获取所有活动信息")
    @GetMapping("getAllInfo")
    public ApiVo<List<ActivityVO>> getAll(){
        return R.ok(activityService.getAll());
    }
    */
    @ApiOperation("根据AID获取资金流向")
    @GetMapping("getInfo")
    public ApiVo<List<FlowVO>> getFlowByAid(@RequestParam(value = "aid", required=false, defaultValue="1") Integer aid){
        return R.ok(flowService.getFlowByAid(aid));
    }

    @ApiOperation("添加资金流向")
    @PostMapping("add")
    public ApiVo<Boolean> createFlow(@RequestBody FlowVO data) throws ParseException {
        return R.ok(flowService.createFlow(data));
    }
/*
    @ApiOperation("获取总募集金额")
    @GetMapping("getTotalMoney")
    public ApiVo<Double> queryTotalMoney(){
        return R.ok(activityService.queryTotalMoney());
    }
*/
}
