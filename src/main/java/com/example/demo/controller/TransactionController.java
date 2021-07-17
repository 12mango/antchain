package com.example.demo.controller;

import com.example.demo.VO.ApiVo;
import com.example.demo.VO.TransactionVO;
import com.example.demo.service.TransactionService;
import com.example.demo.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Api(value = "交易相关", tags = {"交易相关"})
@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @ApiOperation("根据UID获取交易信息列表")
    @GetMapping("getInfo")
    public ApiVo<List<TransactionVO>> getTransactionByUid(@RequestParam(value = "uid", required=false, defaultValue="2") Integer uid){
        return R.ok(transactionService.getTransactionByUid(uid));
    }

    @ApiOperation("用户给活动打钱（交易）")
    @PostMapping("add")
    public ApiVo<Boolean> createTransaction(@RequestBody TransactionVO data) throws ParseException {
        return R.ok(transactionService.createTransaction(data));
    }

}
