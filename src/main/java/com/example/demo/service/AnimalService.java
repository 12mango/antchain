package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Animal;


public interface AnimalService extends IService<Animal>{
    Boolean createAnimal(String name, String type, String actscope);
}
