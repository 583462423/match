package com.sduwh.match.interceptors;

import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qxg on 17-8-29.
 * 获取当前登陆用户的时候，经常要使用Subject，并通过userService的selectByName等方法来获取User信息，不如使用一个拦截器，获取当前User，保存在ThreadLocal中，以后都可以正常获取
 */
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    @Autowired
    TmpRaterService raterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在进来的时候首先判断一下用户是否登陆，因为有shiro控制，所以不管登陆与否，在shiro控制的界面，HostHolder中必然有用户
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            //设置user和rater
            String username = (String) subject.getPrincipal();
            User user = userService.selectByUsername(username);
            hostHolder.setUser(user);

            TmpRater rater = raterService.selectByUsername(username);
            hostHolder.setRater(rater);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //这个是在渲染前会调用的方法
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
