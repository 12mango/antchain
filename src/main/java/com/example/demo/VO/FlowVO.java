package com.example.demo.VO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
@Data
@Accessors(chain = true)
@ApiModel(value = "资金流向参数",description = "资金流向相关参数封装在此对象中")
public class FlowVO {
    private Integer id;
    private Integer aid;

    @ApiModelProperty("转账对应时间")
    private String tm;
    // private Date tm;

    private String description;
    private String url;
    private String hash;
}
