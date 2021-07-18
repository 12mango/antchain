package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.TransactionVO;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.FlowMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.bouncycastle.operator.OperatorCreationException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("TransactionService")
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    private FlowMapper flowMapper;
    private TransactionMapper transactionMapper;
    private ContractService contractService;

    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper, ContractService contractService){
        this.transactionMapper=transactionMapper;
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

        System.out.println(money);

        if(uid==null || aid==null){
            throw new CustomException("用户id或活动id不能为空", ExceptionCode.C0302);
        }

        //transaction.setId(data.getId());
        transaction.setUid(data.getUid());
        transaction.setAid(data.getAid());
        transaction.setMoney(data.getMoney());
        transaction.setTm(StringToDate(data.getTm()));

        //智能合约
        Integer MONEY = (int)(money*100);

        //contractService.initMychainEnv();
        //contractService.initSdk();
        String hash = contractService.callContractReceiveMoney(uid,MONEY,aid,tm).toString();

        //sdk.shutDown();

        transaction.setHash(hash);
        save(transaction);
        return true;
    }
}
