package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.AnimalVO;
import com.example.demo.VO.FileVO;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Flow;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.mapper.AnimalMapper;
import com.example.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnimalService")
public class AnimalServiceImpl extends ServiceImpl<AnimalMapper, Animal> implements AnimalService{
    private AnimalMapper animalMapper;
    private OSSService ossService;

    @Autowired
    public AnimalServiceImpl(AnimalMapper animalMapper,OSSService ossService){
        this.animalMapper = animalMapper;
        this.ossService = ossService;
    }

    @Transactional
    public Boolean createAnimal(String name, String type, String actscope){
        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setActscope(actscope);
        save(animal);
        return true;
    }

    public String uploadFile(FileVO fileVO){
        fileVO.setAid(10000);
        String url = ossService.uploadFile(fileVO);
        Integer id = fileVO.getId();
        Animal animal= animalMapper.selectById(id);
        animal.setUrl(url);
        updateById(animal);
        return url;
    }

    public AnimalVO queryAnimal(Integer id){
        Animal result = animalMapper.selectById(id);
        if(result == null){
            throw new CustomException("查询结果不存在", ExceptionCode.C0302);
        }
        AnimalVO ret = new AnimalVO();
        ret.setId(result.getId());
        ret.setName(result.getName());
        ret.setType(result.getType());
        ret.setActscope(result.getActscope());
        return ret;
    }
}
