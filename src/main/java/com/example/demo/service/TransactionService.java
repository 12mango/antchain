package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.TransactionVO;
import com.example.demo.entity.Transaction;

import java.text.ParseException;
import java.util.List;

public interface TransactionService extends IService<Transaction>{
    public List<TransactionVO> getTransactionByUid(Integer uid);
    public boolean createTransaction(TransactionVO data) throws ParseException;
}
