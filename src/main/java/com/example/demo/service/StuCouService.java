package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.StuScoreVO;
import com.example.demo.entity.*;

import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/4
 */
public interface StuCouService extends IService<StuCou> {
    List<StuScoreVO> getAllScores(String sid,String title);
    Boolean createChooseInfo(String sid,String cid);
}
