package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.AnimalVO;
import com.example.demo.VO.FileVO;
import com.example.demo.entity.Animal;

import java.util.List;


public interface AnimalService extends IService<Animal>{
    Boolean createAnimal(String name, String type, String actscope);
    String uploadFile(FileVO fileVO);
    AnimalVO queryAnimal(Integer id);
    public List<AnimalVO> getAll();
}
