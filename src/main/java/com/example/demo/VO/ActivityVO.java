package com.example.demo.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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


    @ApiModelProperty(value = "用户名",required = false)
    private Integer id;

    //private Integer aid;
    @ApiModelProperty(value = "目标金额")
    private Double target;
    @ApiModelProperty(value = "当前金额")
    private Double now;
    @ApiModelProperty(value = "开始时间",example = "2021-07-16 18:00:00")
    private String start;
    @ApiModelProperty(value = "截止时间",example = "2021-07-16 18:00:00")
    private String end;

    //private Date start;

    //private Date end;
    @ApiModelProperty(value = "主题")
    private String topic;
    @ApiModelProperty(value = "负责人")
    private String director;
    @ApiModelProperty(value = "负责人联系方式")
    private String contact;
    @ApiModelProperty(value = "活动描述")
    private String description;
    @ApiModelProperty(value = "活动图片地址",required=false)
    private String url;
}
