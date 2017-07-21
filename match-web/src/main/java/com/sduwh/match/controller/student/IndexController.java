package com.sduwh.match.controller.student;

import com.sduwh.match.Enum.UserStatus;
import com.sduwh.match.base.BaseController;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qxg on 17-7-21.
 * 学生主页管理器
 * 主页方面，主要展示学生信息，当前帐号状态，各种比赛的阶段等
 */
@Controller
@RequestMapping("/student")
public class IndexController extends BaseController {

    private static final String STUDENT_INDEX = "/student/index";

    @Autowired
    UserService userService;

    /** 显示用户当前帐号状态，如果未激活就提醒激活，显示其余各种信息 */
    @GetMapping("/index")
    public String index(){
        String name = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.selectByUsername(name);
        //获取到User信息后，将User的各种信息展示，需要使用Map

        return STUDENT_INDEX;
    }
}
