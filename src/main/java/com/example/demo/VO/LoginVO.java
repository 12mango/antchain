package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 12mango
 * @since 2021/4/7
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "登录参数",description = "登录用的账号密码封装在此对象中")
public class LoginVO {

    @ApiModelProperty("账户")
    private String loginUsername;

    @ApiModelProperty("密码")
    private String loginPassword;
}
