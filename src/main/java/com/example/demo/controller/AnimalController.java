package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.VO.AnimalVO;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.service.AnimalService;
import com.example.demo.service.StuCouService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private final AnimalService AnimalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.AnimalService = animalService;
    }

    @PostMapping("/save")
    @UserLoginToken
    public Boolean createAnimal(
                                     // @RequestHeader String token,
                                     // @RequestParam(value="id", required=false, defaultValue="") String id,
                                     // @RequestParam(value="name",required=false,defaultValue="") String name,
                                     // @RequestParam(value="type",required=false) String type,
                                     // @RequestParam(value="actscope",required=false) String actscope
                                     @RequestBody AnimalVO animal
                                     ) {
        /*
        if(curName == null){
            curName = "";
        }
        if(teaName == null){
            teaName = "";
        }
        if(field == null){
            field = new ArrayList<Integer>;
        }
         */
        String name = animal.getName();
        String type = animal.getType();
        String actscope = animal.getActscope();

        return AnimalService.createAnimal(name, type, actscope);
    }

}
