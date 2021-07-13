package com.example.demo.VO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 12mango
 * @since 2021/4/5
 */
@Data
@Accessors(chain = true)
public class CourseVO {

    private Integer id;

    private String curname;

    private Integer credit;

    private Integer nowremain;

    private Integer maxremain;

    private String teaname;

    private String field;

    private String day;

    private Integer startweek;

    private Integer endweek;

    private Integer weeklytimes;

    private Integer startsection;

    private Integer endsection;

    private Boolean choose;

}
