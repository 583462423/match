package com.sduwh.match.controller;

import com.sduwh.match.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

/**
 * Created by qxg on 17-6-28.
 */
@Controller
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static String LOGIN = "login";

    @Autowired
    UserService userService;

    @GetMapping(value = {"/login","/","/index"})
    public String login() throws IOException {
        return LOGIN;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult userLogin(User user){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        token.setRememberMe(false);   //不记住我
        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            logger.error("该账号不存在或者该账号未被激活!", e);
            return fail(false, "该账号不存在或者该账号未被激活!");
        } catch (DisabledAccountException e) {
            logger.error("该账号已被冻结!", e);
            return fail(false, "该账号已被冻结!");
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误", e);
            return fail(false, "密码错误");
        } catch (ExcessiveAttemptsException e) {
            logger.error("密码连续输入错误超过5次，锁定半小时!", e);
            return fail(false, "密码连续输入错误超过5次，锁定半小时!");
        }catch (RuntimeException e) {
            logger.error("未知错误,请联系管理员!", e);
            return fail(false, "未知错误,请联系管理员!");
        }

        //这表示通过，但是如何获取通过信息？= =
        if(currentUser.hasRole("student")){
            //如果是user用户，就怎样？
            return success(true,"/xxurl");
        }

        return success(true);
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return LOGIN;
    }

}
