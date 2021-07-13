package com.example.demo.controller;

import com.example.demo.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 12mango
 * @since 2021/5/14
 */
@RestController
@RequestMapping("backup")
public class BackupController {

    @Autowired
    private final BackupService backupService;

    @Autowired
    public BackupController(BackupService backupService){
        this.backupService = backupService;
    }

    @GetMapping("backup")
    public Boolean backup(){
        return backupService.backup();
    }

    @GetMapping("restore")
    public Boolean restore(){
        return backupService.restore();
    }
}
