package com.sduwh.match.controller.academy;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Pass;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by qxg on 17-9-1.
 * 学院管理员控制器
 */
@Controller
@RequestMapping("/academy")
public class AcademyIndexController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AcademyIndexController.class);
    private static final String ACADEMY_INDEX = "/academy/index";
    private static final String CHECK_DETAIL = "/academy/checkDetail";

    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    PassService passService;

    @GetMapping("/index")
    public String index(Map<String,UserWrapper> map){
        User user = hostHolder.getUser();
        String res = userService.checkStatus(user);
        if(res != null)return res;

        //否则就跳转过来，直接开始正常操作
        map.put("user",userService.selectUserWrapperByUser(user));

        //为什么用户的主页信息逻辑都一样，但是还不进行模块化，因为每种用户的详细信息是不一样的。
        map.put("someOtherThing",null);
        return ACADEMY_INDEX;
    }

    /** 待审核比赛的详细信息*/
    @GetMapping("/check/detail/{id}")
    public String checkDetail(Model model, @PathVariable("id") int id){
        //TODO 判断当前比赛是否未审核，如果已审核就设置为未授权
        String res = matchItemService.checkDetail(model,id);
        if(res != null)return res;
        return CHECK_DETAIL;
    }

    /** 审核通过*/
    @PostMapping("/check/detail/pass/{id}")
    @ResponseBody
    public String checkPass(@PathVariable("id") int matchItemId){
        String res = matchItemService.checkPass(matchItemId);
        if(res != null)return res;
        return JSONObject.toJSONString(setResult("success","true"));
    }
}
