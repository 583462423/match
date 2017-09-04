package com.sduwh.match.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.enums.Roles;
import com.sduwh.match.enums.UserStatus;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Role;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.role.RoleService;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.RoleStatus;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by qxg on 17-6-28.
 */
@Controller
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static String LOGIN = "login";

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;

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
        if(currentUser.hasRole(Roles.STUDENT.getName())){
            //如果是user用户，就怎样？
            return success(true,"/student/index");
        }

        if(currentUser.hasRole(Roles.ADMIN.getName())){
            return success(true,"/admin/matchs");
        }

        //guider这个名字起的。。。。哎就这样吧
        if(currentUser.hasRole(Roles.GUIDER.getName())){
            return success(true,"/teacher/index");
        }

        if(currentUser.hasRole(Roles.ACADEMY_ADMIN.getName())){
            return success(true,"/academy/index");
        }

        return success(true);
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return LOGIN;
    }

    /** 用于通过用户名查找本人*/
    @GetMapping("/user/{name}")
    @ResponseBody
    public String getUser(@PathVariable("name") String name){
        User user = userService.selectByUsername(name);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Map<String,String> info = new HashMap<>();

        if(user == null)return "null";
        if(user.getUsername().equals(username)) {
            info.put("error","如果您申请本比赛，那么您就是队长，请勿添加本人");
            return JSONObject.toJSONString(info);
        }
        if(user.getStatus() != UserStatus.NORMAL.getId()){
            info.put("error","该用户帐号目前无法正常使用，请通知该用户");
            return JSONObject.toJSONString(info);
        }

        //判断用户是否是学生，这个需要Role
        Role role = roleService.selectByRoleName("student");
        if(user.getRole().getId() != role.getId()){
            info.put("error","只能添加学生用户！");
            return JSONObject.toJSONString(info);
        }

        info.put("name",user.getName());
        info.put("id",String.valueOf(user.getId()));
        info.put("username",user.getUsername());
        return JSONObject.toJSONString(info);
    }


    /** 用于通过用户名查找教师*/
    @GetMapping("/user/teacher/{name}")
    @ResponseBody
    public String getTeacher(@PathVariable("name") String name){
        User user = userService.selectByUsername(name);
        Map<String,String> info = new HashMap<>();

        if(user == null)return "null";
        if(user.getStatus() != UserStatus.NORMAL.getId()){
            info.put("error","该用户帐号目前无法正常使用，请通知该用户");
            return JSONObject.toJSONString(info);
        }

        //判断用户是否是学生，这个需要Role
        Role role = roleService.selectByRoleName("guider");
        if(user.getRole().getId() != role.getId()){
            info.put("error","只能添加教师账户！");
            return JSONObject.toJSONString(info);
        }


        //如果用户是教师，需要判断教师可以参加项目的大小，但是这个地方没有获取比赛的信息，所以可以将这个信息放info中
        //因为数据库设计问题，导致教师参加了多少个比赛没有办法获取，所以将这个信息放到redis中，在redis中获取到id后，判断当前的比赛是否已经过期
        String key = RedisKeyGenerator.getTeacherProjects();
        Set<String> set = jedisAdapter.sget(key);
        //cnt记录当前教师参与的有效的比赛的个数
        long cnt = set.stream().filter(e->{
            int id = Integer.parseInt(e);
            MatchItem item = matchItemService.selectByPrimaryKey(id);
            int infoId = item.getMatchInfoId();
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(infoId);
            Date nowDate = new Date();
            Date startDate = new Date(matchInfo.getStartTime().getTime());
            Date endDate = new Date(matchInfo.getEndTime().getTime());

            return nowDate.after(startDate) && nowDate.before(endDate);
        }).count();

        info.put("cnt",String.valueOf(cnt));
        info.put("name",user.getName());
        info.put("id",String.valueOf(user.getId()));
        info.put("username",user.getUsername());
        return JSONObject.toJSONString(info);
    }

}
