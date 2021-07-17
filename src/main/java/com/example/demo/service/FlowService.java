package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.FileVO;
import com.example.demo.VO.FlowVO;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Flow;

import java.text.ParseException;
import java.util.List;

public interface FlowService extends IService<Flow>{
    public List<FlowVO> getFlowByAid(Integer aid);
    public boolean createFlow(FlowVO data) throws ParseException;
    public String uploadFile(FileVO fileVO);
}
