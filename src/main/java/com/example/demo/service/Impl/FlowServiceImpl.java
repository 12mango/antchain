package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.FileVO;
import com.example.demo.VO.FlowVO;
import com.example.demo.entity.Flow;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.FlowMapper;
import com.example.demo.service.FlowService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.min;

@Service("FlowService")
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {

    private FlowMapper flowMapper;
    private OSSService ossService;
    private ContractService contractService;

    @Autowired
    public FlowServiceImpl(FlowMapper flowMapper, OSSService ossService, ContractService contractService){
        this.flowMapper=flowMapper;
        this.ossService=ossService;
        this.contractService=contractService;
    }

    public String DateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(date);
        return str;
    }

    public Date StringToDate(String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(str);
        return date;
    }

    public List<FlowVO> getAll(Integer length){
        List<Flow> result = flowMapper.selectList(Wrappers.<Flow>lambdaQuery());
        List<FlowVO> ret = new ArrayList<FlowVO>();
        result.forEach((item)->{
            FlowVO tmp = new FlowVO();
            tmp.setId(item.getId());
            tmp.setAid(item.getAid());
            tmp.setTm(DateToString(item.getTm()));
            tmp.setDescription(item.getDescription());
            tmp.setUrl(item.getUrl());
            tmp.setHash(item.getHash());
            tmp.setMoney(item.getMoney());
            ret.add(tmp);
        });

        //排序
        ret.sort(Comparator.comparing(FlowVO::getTm));
        Collections.reverse(ret);

        return ret.subList(0,min(length.intValue(),ret.size()));
    }

    public List<FlowVO> getFlowByAid(Integer aid){
        List<Flow> result = flowMapper.selectList(Wrappers.<Flow>lambdaQuery());
        List<FlowVO> ret = new ArrayList<FlowVO>();
        result.forEach((item)->{
            if (item.getAid() == aid) {
                FlowVO tmp = new FlowVO();
                tmp.setId(item.getId());
                tmp.setAid(item.getAid());
                tmp.setTm(DateToString(item.getTm()));
                tmp.setDescription(item.getDescription());
                tmp.setUrl(item.getUrl());
                tmp.setHash(item.getHash());
                tmp.setMoney(item.getMoney());
                ret.add(tmp);
            }
        });
        return ret;
    }

    public boolean createFlow(FlowVO data) throws ParseException, IOException {


        Flow flowes = new Flow();

        Integer aid = data.getAid();
        Double money = data.getMoney();
        String tm = data.getTm();
        String description = data.getDescription();

        if(aid==null){
            throw new CustomException("活动id不能为空", ExceptionCode.C0302);
        }

        flowes.setAid(data.getAid());
        flowes.setTm(StringToDate(data.getTm()));
        flowes.setDescription(data.getDescription());
        flowes.setUrl(data.getUrl());
        flowes.setMoney(data.getMoney());
        save(flowes);

        Integer id = flowes.getId();

        Integer MONEY = (int)(money*100);
        String hash = contractService.callContractSpendMoney(MONEY, aid, id, tm).toString();
        flowes.setHash(hash);
        updateById(flowes);

        return true;
    }

    public String uploadFile(FileVO fileVO) throws IOException {

        Integer aid = fileVO.getAid();
        Integer id = fileVO.getId();

        // 智能合约部分

        MultipartFile file = fileVO.getFile();
        byte[] bytesArray = file.getBytes();

        // 获取文件内容的hash
        String fileContentHash = DigestUtils.sha256Hex(bytesArray);

        // 调用智能合约
        String hash = contractService.callContractUploadProvidence(fileContentHash, aid, id,DateToString(new Date())).toString();

        // 文件上传至OSS
        String url = ossService.uploadFile(fileVO);

        Flow flow=flowMapper.selectById(id);
        flow.setUrl(url);
        flow.setHash(hash);
        updateById(flow);

        return url;
    }

    public boolean isAllUpload(Integer aid) {
        List<Flow> result = flowMapper.selectList(Wrappers.<Flow>lambdaQuery().eq(Flow::getAid,aid));
        AtomicBoolean flag = new AtomicBoolean(true);
        result.forEach((item)->{
            if (item.getUrl() == null) {
                flag.set(false);
            }
        });
        return flag.get();
    }
}
