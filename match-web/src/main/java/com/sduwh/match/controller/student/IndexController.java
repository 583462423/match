package com.sduwh.match.controller.student;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by qxg on 17-7-21.
 * 学生主页管理器
 * 主页方面，主要展示学生信息，当前帐号状态，各种比赛的阶段等
 */
@Controller
@RequestMapping("/student")
public class IndexController extends BaseController {

    private static final String STUDENT_INDEX = "/student/index";
    private static final String STUDENT_TO_ACTIVE = "";
    @Autowired
    UserService userService;

    /** 显示用户当前帐号状态，如果未激活就提醒激活，显示其余各种信息 */
    @GetMapping("/index")
    public String index(Map<String,UserWrapper> map){
        String name = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.selectByUsername(name);
        //获取到User信息后，将User的各种信息展示，需要使用Map
        //判断用户是否激活，如果没有激活，则跳转到激活网站，主要是跳转过去用于激活
        switch (user.getStatus()){
            case 2:
                //状态2为冻结，所以无权访问
                return UNAUTH;
            case 3:
                //TODO 状态3为未激活，所以需要跳转到某个页面激活，待做
                return STUDENT_TO_ACTIVE;
        }
        //否则就跳转过来，直接开始正常操作
        map.put("user",userService.selectUserWrapperByUser(user));
        return STUDENT_INDEX;
    }

}
