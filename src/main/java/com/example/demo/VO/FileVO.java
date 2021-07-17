package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 12mango
 * @since 2021/7/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "凭证",description = "上传凭证所用的数据封装在此对象")
public class FileVO {
    private MultipartFile file;
    private Integer id;
    private Integer aid;
}
