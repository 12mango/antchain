package com.example.demo.VO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
@Data
@Accessors(chain = true)
@ApiModel(value = "活动参数",description = "活动相关参数封装在此对象中")
public class FlowVO {
    private Integer id;
    private Integer aid;
    private String tm;
    // private Date tm;
    private String description;
    private String url;
    private String hash;
}
