package com.example.demo.controller;

import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.ApiVo;
import com.example.demo.VO.FileVO;
import com.example.demo.VO.FlowVO;
import com.example.demo.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.R;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "资金流向", tags = {"资金流向相关"})
@RestController
@RequestMapping("flow")
public class FlowController {
    @Autowired
    private final FlowService flowService;

    @Autowired
    public FlowController(FlowService flowService){
        this.flowService=flowService;
    }

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

    @ApiOperation("上传凭证")
    @PostMapping("upload")
    public ApiVo<Map<String,String>> createFlow(@RequestBody FileVO fileVO) throws ParseException {
        String url = flowService.uploadFile(fileVO);
        Map<String,String> ret=new HashMap<String,String>();
        ret.put("url",url);
        return R.ok(ret);
    }
}
