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

@Api(value = "事务相关", tags = {"用于事务的相关接口"})
@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    /*
    @ApiOperation("获取所有活动信息")
    @GetMapping("getAllInfo")
    public ApiVo<List<ActivityVO>> getAll(){
        return R.ok(activityService.getAll());
    }
    */
    @ApiOperation("根据UID获取事务信息")
    @GetMapping("getInfo")
    public ApiVo<List<TransactionVO>> getTransactionByUid(@RequestParam(value = "uid", required=false, defaultValue="2") Integer uid){
        return R.ok(transactionService.getTransactionByUid(uid));
    }

    @ApiOperation("添加事务")
    @PostMapping("add")
    public ApiVo<Boolean> createTransaction(@RequestBody TransactionVO data) throws ParseException {
        return R.ok(transactionService.createTransaction(data));
    }
/*
    @ApiOperation("获取总募集金额")
    @GetMapping("getTotalMoney")
    public ApiVo<Double> queryTotalMoney(){
        return R.ok(activityService.queryTotalMoney());
    }
*/
}
