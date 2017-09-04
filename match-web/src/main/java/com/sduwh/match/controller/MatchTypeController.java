package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.service.matchtype.MatchTypeService;
import com.sduwh.match.service.matchtype2.MatchType2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 * 获取比赛类型信息
 */
@Controller
@RequestMapping("type")
public class MatchTypeController extends BaseController{

    @Autowired
    MatchTypeService matchTypeService;

    @Autowired
    MatchType2Service matchType2Service;

    @GetMapping("/type1")
    @ResponseBody
    public List<MatchType> getALlType(){
        return matchTypeService.selectAll();
    }

    @GetMapping("/type1/type2/{id}")
    @ResponseBody
    public List<MatchType2> getAllType2ById(@PathVariable("id") Integer type1Id){
        return matchType2Service.selectByType1Id(type1Id);
    }
}
