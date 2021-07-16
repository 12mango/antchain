package com.example.demo.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 12mango
 * @since 2021/7/16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "活动参数",description = "活动相关参数封装在此对象中")
public class ActivityVO {

    private Integer id;

    //private Integer aid;

    private Double target;

    private Double now;

    private String start;

    private String end;

    //private Date start;

    //private Date end;

    private String topic;
}
