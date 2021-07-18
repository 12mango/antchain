package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.TransactionVO;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.ActivityMapper;
import com.example.demo.mapper.FlowMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.service.ActivityService;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("TransactionService")
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    private FlowMapper flowMapper;
    private TransactionMapper transactionMapper;
    private ContractService contractService;
    private ActivityMapper activityMapper;
    private ActivityService activityService;

    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper, ContractService contractService,ActivityMapper activityMapper,ActivityService activityService){
        this.transactionMapper=transactionMapper;
        this.contractService=contractService;
        this.activityMapper=activityMapper;
        this.activityService=activityService;
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

    public List<TransactionVO> getTransactionByUid(Integer uid){
        List<Transaction> result = transactionMapper.selectList(Wrappers.<Transaction>lambdaQuery());
        List<TransactionVO> ret = new ArrayList<TransactionVO>();
        result.forEach((item)->{
            if(item.getUid() == uid) {
                TransactionVO tmp = new TransactionVO();
                tmp.setId(item.getId());
                tmp.setAid(item.getAid());
                tmp.setTm(DateToString(item.getTm()));
                tmp.setMoney(item.getMoney());
                tmp.setUid(item.getUid());
                tmp.setHash(item.getHash());
                ret.add(tmp);
            }
        });

        //排序
        ret.sort(Comparator.comparing(TransactionVO::getTm));
        Collections.reverse(ret);
        return ret;
    }

    public boolean createTransaction(TransactionVO data) throws ParseException, IOException {
        Transaction transaction = new Transaction();

        //Integer id = data.getId();
        Integer uid = data.getUid();
        Integer aid = data.getAid();


        Double money = data.getMoney();
        String tm = data.getTm();

        //System.out.println(money);

        if(uid==null || aid==null){
            throw new CustomException("用户id或活动id不能为空", ExceptionCode.C0302);
        }

        synchronized(this){
            //transaction.setId(data.getId());
            transaction.setUid(data.getUid());
            transaction.setAid(data.getAid());
            transaction.setMoney(data.getMoney());
            transaction.setTm(StringToDate(data.getTm()));

            Activity activity = activityMapper.selectById(aid);
            if(activity==null){
                throw new CustomException("活动不存在", ExceptionCode.C0302);
            }

            Double now = activity.getNow();
            Double target = activity.getTarget();;
            Double minus = target - now;
            if(minus<money){
                throw new CustomException("转账过多", ExceptionCode.C0302);
                //这里应该执行一个退款的程序
            }
            now = now + money;

            activity.setNow(now);
            activityMapper.updateById(activity);
            //activityService.Save(activity);

            //智能合约
            Integer MONEY = (int)(money*100);

            String hash = contractService.callContractReceiveMoney(uid,MONEY,aid,tm).toString();

            transaction.setHash(hash);
            save(transaction);
        }

        return true;
    }
}
