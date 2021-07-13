package com.example.demo.VO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 12mango
 * @since 2021/4/4
 */
@Data
@Accessors(chain = true)
public class StuScoreVO{
    private String sid;
    private String cid;
    private String name;
    private Integer task1;
    private Integer task2;
    private Integer task3;
    private Integer finalscore;
    private Integer score;
    private Integer credit;
    private String teaName;
    private String CurName;
}
