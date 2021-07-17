package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.TransactionVO;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.FlowMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("TransactionService")
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    private FlowMapper flowMapper;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper){
        this.transactionMapper=transactionMapper;
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
        return ret;
    }

    public boolean createTransaction(TransactionVO data) throws ParseException {
        Transaction transaction = new Transaction();
        transaction.setId(data.getId());
        transaction.setUid(data.getUid());
        transaction.setAid(data.getAid());
        transaction.setMoney(data.getMoney());
        transaction.setTm(StringToDate(data.getTm()));

        //智能合约
        String Hash="";

        transaction.setHash(Hash);
        save(transaction);
        return true;
    }
/*
    public double queryTotalMoney(){
        double ret = 0.0;
        ret = activityMapper.selectList(Wrappers.<Activity>lambdaQuery()).stream().mapToDouble(Activity::getNow).sum();
        return ret;
    }
 */
}
