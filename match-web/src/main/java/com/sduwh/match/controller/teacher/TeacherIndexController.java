package com.sduwh.match.controller.teacher;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.enums.UserStatus;
import com.sduwh.match.exception.base.BaseException;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Pass;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxg on 17-8-30.
 * 教师的控制层
 */
@Controller
@RequestMapping("/teacher")
public class TeacherIndexController extends BaseController{
    private static final String TEACHER_INDEX = "/teacher/index";
    private static final String TEACHER_TO_ACTIVE = "";
    private static final String CHECK_DETAIL = "/teacher/checkDetail";
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    PassService passService;
    @Autowired
    StageService stageService;

    /** 显示用户当前帐号状态，如果未激活就提醒激活，显示其余各种信息 */
    @GetMapping("/index")
    public String index(Map<String,UserWrapper> map){
        User user = hostHolder.getUser();
        //获取到User信息后，将User的各种信息展示，需要使用Map
        //判断用户是否激活，如果没有激活，则跳转到激活网站，主要是跳转过去用于激活
        String res = userService.checkStatus(user);
        if(res != null)return res;

        //否则就跳转过来，直接开始正常操作
        map.put("user",userService.selectUserWrapperByUser(user));
        return TEACHER_INDEX;
    }

    /** 待审核比赛的详细信息*/
    @GetMapping("/check/detail/{id}")
    public String checkDetail(Model model, @PathVariable("id") int id){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.GUIDER_VERIFY.getId()){
            return UNAUTH;
        }

        //通过比赛id来查找比赛信息
        String res = matchItemService.checkDetail(model,id);
        if(res != null)return res;
        return CHECK_DETAIL;
    }

    @PostMapping("/check/detail/pass/{id}")
    @ResponseBody
    public String checkPass(@PathVariable("id") int matchItemId){
        String res = matchItemService.checkPass(matchItemId,MatchItemService.CHECK_PASS_TEACHER);
        if(res != null)return res;
        return JSONObject.toJSONString(setResult("success","true"));
    }

    @PostMapping("/check/detail/nopass/{id}")
    @ResponseBody
    public String checkNoPass(@PathVariable("id") int matchItemId){
        //判断当前阶段
        String res = matchItemService.checkNoPass(matchItemId,MatchItemService.CHECK_PASS_TEACHER);
        if(res != null)return res;
        return JSONObject.toJSONString(setResult("success","true"));
    }
}
