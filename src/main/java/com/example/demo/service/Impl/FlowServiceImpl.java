package com.example.demo.service.Impl;

import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.FileVO;
import com.example.demo.VO.FlowVO;
import com.example.demo.entity.Flow;
import com.example.demo.mapper.FlowMapper;
import com.example.demo.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("FlowService")
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {

    private FlowMapper flowMapper;
    private OSSService ossService;

    @Autowired
    public FlowServiceImpl(FlowMapper flowMapper, OSSService ossService){
        this.flowMapper=flowMapper;
        this.ossService=ossService;
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

    public List<FlowVO> getAll(){
        List<Flow> result = flowMapper.selectList(Wrappers.<Flow>lambdaQuery());
        List<FlowVO> ret = new ArrayList<FlowVO>();
        result.forEach((item)->{
            FlowVO tmp = new FlowVO();
            tmp.setId(item.getId());
            tmp.setAid(item.getAid());
            tmp.setTm(DateToString(item.getTm()));
            tmp.setDescription(item.getDescription());
            tmp.setUrl(item.getUrl());
            tmp.setHash(item.getHash());
            ret.add(tmp);
        });
        ret.sort(Comparator.comparing(FlowVO::getTm));
        Collections.reverse(ret);
        return ret;
    }

    public List<FlowVO> getFlowByAid(Integer aid){
        List<Flow> result = flowMapper.selectList(Wrappers.<Flow>lambdaQuery());
        List<FlowVO> ret = new ArrayList<FlowVO>();
        result.forEach((item)->{
            if (item.getAid() == aid) {
                FlowVO tmp = new FlowVO();
                tmp.setId(item.getId());
                tmp.setAid(item.getAid());
                tmp.setTm(DateToString(item.getTm()));
                tmp.setDescription(item.getDescription());
                tmp.setUrl(item.getUrl());
                tmp.setHash(item.getHash());
                ret.add(tmp);
            }
        });
        return ret;
    }

    public boolean createFlow(FlowVO data) throws ParseException {
        Flow flowes = new Flow();
        flowes.setId(data.getId());
        flowes.setAid(data.getAid());
        flowes.setTm(StringToDate(data.getTm()));
        flowes.setDescription(data.getDescription());
        flowes.setUrl(data.getUrl());
        flowes.setHash(data.getHash());
        save(flowes);
        return true;
    }

    public String uploadFile(FileVO fileVO){

        //智能合约部分

        String url = ossService.uploadFile(fileVO);
        Integer id = fileVO.getId();
        Flow flow=flowMapper.selectById(id);
        flow.setUrl(url);
        updateById(flow);

        return url;
    }
}
