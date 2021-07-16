package com.example.demo.controller;

import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                     @RequestParam(value="name",required=false,defaultValue="") String name,
                                     @RequestParam(value="type",required=false) String type,
                                     @RequestParam(value="actscope",required=false) String actscope) {
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
        return AnimalService.createAnimal(name, type, actscope);
    }

}
