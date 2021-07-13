package com.example.demo.VO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 12mango
 * @since 2021/4/5
 */
@Data
@Accessors(chain = true)
public class StudentVO {
    private String sid;

    private String name;

    private String sex;

    private String school;

    private String major;

    private String phone;

    private String password;

    private String email;
}
