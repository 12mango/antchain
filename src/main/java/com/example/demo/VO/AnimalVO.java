package com.example.demo.VO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AnimalVO {
    private Integer id;
    private String name;
    private String type;
    private String actscope;
}
