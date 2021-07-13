package com.example.demo.VO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 12mango
 * @since 2021/4/7
 */
@Data
@Accessors(chain = true)
public class LoginVO {
    private String loginUsername;
    private String loginPassword;
}
