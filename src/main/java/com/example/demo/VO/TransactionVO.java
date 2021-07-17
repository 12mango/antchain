package com.example.demo.VO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "事务参数",description = "事务相关参数封装在此对象中")
public class TransactionVO {
    private Integer id;
    private Integer uid;
    private Integer aid;
    private Double money;
    private String tm;
    private String hash;
}
