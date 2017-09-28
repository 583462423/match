package com.sduwh.match.controller;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.Config;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.enums.Roles;
import com.sduwh.match.enums.UserStatus;
import com.sduwh.match.exception.UserBadOperationException;
import com.sduwh.match.exception.UserWrongOperationException;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Role;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.MailSender;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.role.RoleService;
import com.sduwh.match.service.rsa.RSAService;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.MD5Utils;
import com.sduwh.match.util.RandomStringUtils;
import com.sduwh.match.util.RegexUtils;
import com.sduwh.match.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * Created by qxg on 17-6-28.
 */
@Controller
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String LOGIN = "login";
    private static final String ACTIVE = "active";

    @Autowired
    UserService userService;
    @Autowired
    RSAService rsaService;
    @Autowired
    RoleService roleService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    MailSender mailSender;

    @GetMapping(value = {"/login","/","/index"})
    public String login() throws IOException {
        return LOGIN;
    }

    @GetMapping(value = "/pubkey")
    @ResponseBody
    public ResponseResult getPublicKey(HttpServletRequest req) {
        RSAService.RSAKeyPair keyPair = rsaService.rsaCreateKeyPair();
        // promise there is session associates with request
        req.getSession().setAttribute("prikey", keyPair.privateKey);
        return success(true, keyPair.publicKeyStr);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult userLogin(User user, HttpServletRequest req){
        Subject currentUser = SecurityUtils.getSubject();
//        token.setRememberMe(false);   //不记住我 the default is false
        try {
            UsernamePasswordToken token = createUsernamePasswordToken(user, req);
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            logger.error("账户不存在或密码错误!", e);
            return fail(false, "账户不存在或密码错误!");
        } catch (DisabledAccountException e) {
            logger.error("该账号已被冻结!", e);
            return fail(false, "该账号已被冻结!");
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误", e);
            return fail(false, "账户不存在或密码错误");
        } catch (UserBadOperationException e) {
            logger.error("用户非法操作", e);
            return fail(false, "账户不存在或密码错误");
        } catch (UserWrongOperationException e) {
            logger.error("用户错误操作", e);
            return fail(false, "Connection or cookies reset?");
        } catch (RuntimeException e) {
            logger.error("未知错误,请联系管理员!", e);
            return fail(false, "未知错误,请联系管理员!");
        }

        req.getSession().removeAttribute("prikey");

        //这表示通过，但是如何获取通过信息？= =
        if(currentUser.hasRole(Roles.STUDENT.getName())){
            //如果是student，跳转到student界面
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

    /** 用于用户激活，使用邮箱进行激活*
     * 激活规则说明，向激活的邮箱发送 http://Config.URL/username/MD5(自动生成的激活码)
     * 在接受到这个请求后，先查找该用户对应的激活码，然后将激活码进行md5，之后对比两者是否相同，相同即激活。
     */
    @GetMapping("/active")
    public String getActivePage(Model model){
        User user = getUser();
        if(user == null || user.getStatus() != UserStatus.NOT_ACTIVE.getId())return UNAUTH;
        model.addAttribute("user",user);
        String emailKey = RedisKeyGenerator.getUserTmpEmail();
        String userEmail = jedisAdapter.hget(emailKey,user.getUsername());
        System.out.println("userEmail :" + userEmail);
        if(StringUtils.nullOrEmpty(userEmail)){
            model.addAttribute("emailIsNull","true");
        }else{
            model.addAttribute("email",userEmail);
        }
        return ACTIVE;
    }

    /** 给当前用户设置email*/
    @PostMapping("/active/email")
    @ResponseBody
    public String setEmail(@RequestParam("email")String email){
        if(!RegexUtils.checkEmaile(email))return setJsonResult("error","email格式不正确");
        User user = getUser();
        if(user == null)return setJsonResult("error","请先登陆");

        String hkey = RedisKeyGenerator.getUserTmpEmail();
        jedisAdapter.hset(hkey,user.getUsername(),email);
        return setJsonResult("success","true");
    }

    /**向该id对应的用户发送激活码*/
    @PostMapping("/active/user")
    @ResponseBody
    public String active(@RequestParam("userId")int userId){
        User user = userService.selectByPrimaryKey(userId);
        if(user == null)return setJsonResult("error","失败");
        String emailKey = RedisKeyGenerator.getUserTmpEmail();
        String tmpEmail = jedisAdapter.hget(emailKey,user.getUsername());
        if(StringUtils.nullOrEmpty(tmpEmail))return setJsonResult("error","邮箱未设置");

        //生成随即码
        String randomString = RandomStringUtils.getRandomStringOfEmail();
        String hkey = RedisKeyGenerator.getUserActive();
        jedisAdapter.hset(hkey,user.getUsername(),randomString);

        //通过激活码和username和email设置md5,向用户email发送对应的激活链接
        String activeUrl = Config.URL + "/active/" + user.getUsername() + "/" + MD5Utils.getActiveMD5(user.getUsername(),randomString,tmpEmail);
        mailSender.send(tmpEmail,"请点击邮件中的链接激活您的账户",activeUrl);
        return setJsonResult("success","true");
    }

    @GetMapping("/active/{username}/{md5}")
    @ResponseBody
    public String getActive(@PathVariable("username")String username,@PathVariable("md5")String knownMd5){
        //通过username获取User
        User user = userService.selectByUsername(username);
        if(user == null)return UNAUTH;
        //获取 激活码
        String activeHKey = RedisKeyGenerator.getUserActive();
        String activeKey = jedisAdapter.hget(activeHKey,username);
        String emailHKey = RedisKeyGenerator.getUserTmpEmail();
        String email = jedisAdapter.hget(emailHKey,username);
        String md5 = MD5Utils.getActiveMD5(user.getUsername(),activeKey,email);
        if(md5.equals(knownMd5)){
            //已激活，将redis中设置的临时邮箱设置进去
            user.setEmail(email);
            user.setStatus(UserStatus.NORMAL.getId());
            userService.updateByPrimaryKey(user);
            //将临时email和激活码消除
            jedisAdapter.hrem(activeHKey,username);
            jedisAdapter.hrem(emailHKey,username);
            return "激活成功!";
        }
        return "激活失败";
    }

    /**获取当前登陆的user*/
    private User getUser(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            //设置user和rater
            String username = (String) subject.getPrincipal();
            User user = userService.selectByUsername(username);
            return user;
        }
        return null;
    }

    private final UsernamePasswordToken createUsernamePasswordToken(User user, HttpServletRequest req) {
        PrivateKey privateKey = (PrivateKey) req.getSession().getAttribute("prikey");
        if (privateKey == null)
            throw new UserWrongOperationException("用户对应的 private key 没有了");

        UsernamePasswordToken token;
        try {
            System.out.println(rsaService.rsaDecode(user.getPassword(), privateKey));
            token = new UsernamePasswordToken(
                            user.getUsername(),
                            rsaService.rsaDecode(user.getPassword(), privateKey).trim());
        } catch ( InvalidKeySpecException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException
                | NoSuchAlgorithmException e) {
            throw new UserBadOperationException("用户蓄意令密码不合法", e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return token;
    }
}
