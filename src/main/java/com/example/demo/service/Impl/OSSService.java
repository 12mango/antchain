package com.example.demo.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.VO.FileVO;
import com.example.demo.mapper.FlowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author 12mango
 * @since 2021/4/12
 */
@Service
public class OSSService {
    static String endpoint = "oss-cn-beijing.aliyuncs.com";
    static String accessKeyId = "LTAI4G5wmonszAo2pPTYW7gr";
    static String accessKeySecret = "qrG4FL3FgBUGZbEPBMQhYSBqEVoUKl";
    static String bucketName = "albatross-photo";

    private final FlowMapper flowMapper;

    public OSSService(FlowMapper fileMapper){
        this.flowMapper = fileMapper;
    }

    public String urlToFileName(String url){ //只取authId_后和.pdf前的部分
        return url.substring(url.indexOf("_") + 1, url.lastIndexOf("."));
    }

    public String urlToSuffix(String url){ //取.之后的后缀
        return url.substring(url.lastIndexOf(".") + 1);
    }

    public String uploadOSS(MultipartFile file, Integer aid,Integer id) throws IOException {
        //上传至阿里云OSS
        OSS ossClient = new OSSClientBuilder().build("http://"+endpoint, accessKeyId, accessKeySecret);
        String fileName = aid + "_" + id + urlToSuffix(file.getOriginalFilename());
        String url = bucketName + "." + endpoint + "/" + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return url;
    }

    public String uploadFile(FileVO fileVO) {
        MultipartFile file=fileVO.getFile();
        Integer aid=fileVO.getAid();
        Integer id=fileVO.getId();
        if(file == null){
            return "";
        }
        if (!file.isEmpty()) {
            try {
                String url = uploadOSS(file, aid, id);
                return url;

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    /**
     *
     * @param fileId 待删除文件ID
     * @return ok
     */
    /*
    public boolean deleteFile(long fileId,String name,long authId) {

        File file = this.fileMapper.selectOne(Wrappers
                .<File>lambdaQuery()
                .eq(File::getId, fileId)
        );


        File file = this.fileMapper.selectOne(Wrappers
                .<File>lambdaQuery()
                .eq(File::getName, name)
        );

        if(file == null){
            return false;
        }

        String fileName = authId + "_" + file.getName() + "." + file.getSuffix() ;
        // String url = bucketName + "." + endpoint + "/" + fileName;
        OSS ossClient = new OSSClientBuilder().build("http://" + endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();

        //删除数据库信息
        QueryWrapper<File> wrapper = new QueryWrapper<>(file);
        fileMapper.delete(wrapper);
        return true;
    }
    */

}
