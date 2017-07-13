package com.sduwh.match.controller;

//import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
@Controller
@RequestMapping("/stage")
public class StageController {

    @Autowired
    StageService stageService;

    /** 不允许修改Stage,Stage在创建的时候就已确定，否则会出问题。*/
    @GetMapping("/{ids}")
    @ResponseBody
    public List<Stage> getStageByString(@PathVariable("ids") String ids){
        return stageService.selectAllByString(ids);
    }


}
