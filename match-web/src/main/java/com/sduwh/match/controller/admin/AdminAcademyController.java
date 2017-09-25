package com.sduwh.match.controller.admin;

import com.sduwh.match.enums.AcademyTypeEnum;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.model.entity.Academy;
import com.sduwh.match.service.academy.AcademyService;
import com.sduwh.match.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qxg on 17-7-20.
 * 学院管理
 */
@Controller
@RequestMapping("/admin/academy")
public class AdminAcademyController extends BaseController{

    private static final String ACADEMY_ALL = "/admin/academy";
    private static final String ACADEMY_ADD = "/admin/academyAdd";

    @Autowired
    AcademyService academyService;


    @GetMapping("/all")
    public String all(Map<String,List<Academy>> map){
        map.put("academy",academyService.selectAll());
        return ACADEMY_ALL;
    }

    @GetMapping("/add")
    public String add(Map<String,Map<Integer,String>> map){
        Map<Integer,String> academyType = new HashMap<>();
        Arrays.stream(AcademyTypeEnum.values()).forEach(e->{
            academyType.put(e.getId(),e.getType());
        });
        map.put("type",academyType);
        return ACADEMY_ADD;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseResult startToAdd(@RequestParam("type")Integer type,
                                     @RequestParam("name")String name){
        System.out.println(type);
        if(StringUtils.nullOrEmpty(name))return fail(false,"name不能为空");
        if(type == null)return fail(false,"type为必填项");
        Academy academy = new Academy();
        academy.setName(name);
        academy.setType(type);
        if(academyService.insert(academy) == 1) return success(true,"添加成功");
        return fail(false,"添加失败");

        //TODO 2017.7.20 22：36 累了，准备睡觉，明天做下一个功能
    }

}
