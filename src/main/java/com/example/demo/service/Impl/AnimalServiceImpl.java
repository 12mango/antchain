package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Animal;
import com.example.demo.mapper.AnimalMapper;
import com.example.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnimalService")
public class AnimalServiceImpl extends ServiceImpl<AnimalMapper, Animal> implements AnimalService{
    private AnimalMapper animalMapper;

    @Autowired
    public AnimalServiceImpl(AnimalMapper animalMapper){
        this.animalMapper = animalMapper;
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
}
