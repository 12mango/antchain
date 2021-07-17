package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Flow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlowMapper  extends BaseMapper<Flow> {
}
