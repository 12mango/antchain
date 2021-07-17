package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.VO.ActivityVO;
import com.example.demo.VO.AnimalVO;
import com.example.demo.VO.ApiVo;
import com.example.demo.VO.FileVO;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.service.AnimalService;
import com.example.demo.service.StuCouService;
import com.example.demo.service.StudentService;
import com.example.demo.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "动物相关", tags = {"动物信息相关"})
@RestController
@RequestMapping("animals")
public class AnimalController {
    @Autowired
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @ApiOperation("添加动物信息")
    @PostMapping("save")
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

        return animalService.createAnimal(name, type, actscope);
    }

    @ApiOperation("根据ID获取动物信息")
    @GetMapping("getInfo")
    public ApiVo<AnimalVO> getActivity(@RequestParam Integer id){
        return R.ok(animalService.queryAnimal(id));
    }

    @ApiOperation("上传动物照片")
    @PostMapping("upload")
    public ApiVo<Map<String,String>> uploadImg(@RequestBody FileVO fileVO) throws ParseException {
        String url = animalService.uploadFile(fileVO);
        Map<String,String> ret=new HashMap<String,String>();
        ret.put("url",url);
        return R.ok(ret);
    }



}
