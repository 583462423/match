package com.sduwh.match.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.exception.base.BaseException;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by qxg on 17-9-4.
 */
@Controller
@RequestMapping("/admin")
public class AdminCheckController extends BaseController{

    private static final String CHECK_DETAIL = "/admin/checkDetail";
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    StageService stageService;

    /** 查询比赛的详细信息*/
    @GetMapping("/check/detail/{id}")
    public String checkDetail(Model model, @PathVariable("id") int id){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.SCHOOL_VERIFY.getId()){
            return UNAUTH;
        }

        //通过比赛id来查找比赛信息
        String res = matchItemService.checkDetail(model,id);
        if(res != null)return res;
        return CHECK_DETAIL;
    }

    /** 审核通过*/
    @PostMapping("/check/detail/pass/{id}")
    @ResponseBody
    public String checkPass(@PathVariable("id") int matchItemId){
        String res = matchItemService.checkPass(matchItemId,MatchItemService.CHECK_PASS_CAMPUS);
        if(res != null)return res;
        return JSONObject.toJSONString(setResult("success","true"));
    }

    /** 审核不通过*/
    @PostMapping("/check/detail/nopass/{id}")
    @ResponseBody
    public String checkNoPass(@PathVariable("id") int matchItemId){
        String res = matchItemService.checkNoPass(matchItemId,MatchItemService.CHECK_PASS_CAMPUS);
        if(res != null)return res;
        return JSONObject.toJSONString(setResult("success","true"));
    }

}
