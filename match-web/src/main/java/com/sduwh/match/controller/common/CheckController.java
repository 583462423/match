package com.sduwh.match.controller.common;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.enums.Roles;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.stage.StageService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-8-31.
 * 比赛审核的控制器
 */
@Controller
@RequestMapping("/check")
public class CheckController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CheckController.class);
    private static final String TEACHER_CHECK = "/teacher/check";
    private static final String TEACHER_CHECKED = "/teacher/checked";
    private static final String ACADEMY_CHECK = "/academy/check";
    private static final String ACADEMY_CHECKED = "/academy/checked";
    private static final String ADMIN_CHECK = "/admin/check";
    private static final String ADMIN_CHECKED = "/admin/checked";

    @Autowired
    HostHolder hostHolder;
    @Autowired
    StageService stageService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    PassService passService;

    /** 指导老师审核*/
    @GetMapping("/teacher")
    public String teacherCheck(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.GUIDER.getName())){
            return UNAUTH;
        }

        User user = hostHolder.getUser();
        //查询待审核的比赛条目
        //首先查找符合条件的stage
        List<Stage> stages = stageService.selectCheckedStageByStageFlag(MatchStage.GUIDER_VERIFY.getId());
        //通过stages的id去查找哪个比赛是符合条件的，并且还需要判断这个比赛是否已经结束
        List<MatchItem> matchItems = stages.stream().flatMap(e->{
            return matchItemService.selectByStageId(e.getId()).stream();
        }).filter(item->{
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(item.getMatchInfoId());
            return matchInfo.getEndTime().after(new Date());
        }).filter(item->{
            //判断该比赛的指导老师是否是当前用户
            String[] teacherIds = item.getTeacherIds().split(",");
            return Arrays.stream(teacherIds).anyMatch(e->e.equals(String.valueOf(user.getId())));
        }).filter(item->{
            //判断当前用户是否已经审核过该用户了,如果没审核过，那才符合条件！
            Pass pass = passService.selectByUserAndItem(user.getId(),item.getId());
            return !passService.checkPass(pass);
        })
        .collect(Collectors.toList());

        //将需要审核的比赛，放入model中
        map.put("matchItem",matchItems);
        return TEACHER_CHECK;
    }

    /** 指导老师已审核的比赛*/
    @GetMapping("/teacher/done")
    public String teacherCheckDone(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.GUIDER.getName())){
            return UNAUTH;
        }
        List<Pass> passes = passService.selectByUserId(hostHolder.getUser().getId());
        //将已审核的作品的id显示出来
        List<MatchItem> matchItems = passes.stream().map(e->{
            return matchItemService.selectByPrimaryKey(e.getMatchItemId());
        }).collect(Collectors.toList());
        map.put("matchItem",matchItems);
        return TEACHER_CHECKED;
    }


    /** 学院审核，查找待审核的作品*/
    @GetMapping("/academy")
    public String academyCheck(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.ACADEMY_ADMIN.getName())){
            return UNAUTH;
        }

        User user = hostHolder.getUser();
        //查询待审核的比赛条目
        //首先查找符合条件的stage
        List<Stage> stages = stageService.selectCheckedStageByStageFlag(MatchStage.ACADEMY_VERIFY.getId());
        //通过stages的id去查找哪个比赛是符合条件的，并且还需要判断这个比赛是否已经结束
        List<MatchItem> matchItems = stages.stream().flatMap(e->{
            return matchItemService.selectByStageId(e.getId()).stream();
        }).filter(item->{
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(item.getMatchInfoId());
            return matchInfoService.checkIsRunning(matchInfo);
        }).filter(item->{
            //判断这个比赛是否是当前学院，首先要查找到学院ID
            return user.getAcademyId() == item.getAcademyId();
        }).filter(item->{
            //判断当前用户是否已经审核过该用户了,如果没审核过，那才符合条件！
            Pass pass = passService.selectByUserAndItem(user.getId(),item.getId());
            return !passService.checkPass(pass);
        }).collect(Collectors.toList());

        //将需要审核的比赛，放入model中
        map.put("matchItem",matchItems);
        return ACADEMY_CHECK;
    }

    /** 学院管理员已审核的比赛*/
    @GetMapping("/academy/done")
    public String academyCheckDone(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.ACADEMY_ADMIN.getName())){
            return UNAUTH;
        }
        List<Pass> passes = passService.selectByUserId(hostHolder.getUser().getId());
        //将已审核的作品的id显示出来
        List<MatchItem> matchItems = passes.stream().map(e->{
            return matchItemService.selectByPrimaryKey(e.getMatchItemId());
        }).collect(Collectors.toList());
        map.put("matchItem",matchItems);
        return ACADEMY_CHECKED;
    }


    /** 学校审核，或成为超级管理员审核，查找待审核的作品*/
    @GetMapping("/admin")
    public String adminCheck(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.ADMIN.getName())){
            return UNAUTH;
        }

        User user = hostHolder.getUser();
        //查询待审核的比赛条目
        //首先查找符合条件的stage
        List<Stage> stages = stageService.selectCheckedStageByStageFlag(MatchStage.SCHOOL_VERIFY.getId());
        //通过stages的id去查找哪个比赛是符合条件的，并且还需要判断这个比赛是否已经结束
        List<MatchItem> matchItems = stages.stream().flatMap(e->{
            return matchItemService.selectByStageId(e.getId()).stream();
        }).filter(item->{
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(item.getMatchInfoId());
            return matchInfo.getEndTime().after(new Date());
        }).filter(item->{
            //判断当前用户是否已经审核过该用户了,如果没审核过，那才符合条件！
            Pass pass = passService.selectByUserAndItem(user.getId(),item.getId());
            return !passService.checkPass(pass);
        }).collect(Collectors.toList());

        //将需要审核的比赛，放入model中
        map.put("matchItem",matchItems);
        return ADMIN_CHECK;
    }

    /** 学校或超级管理员已审核的比赛*/
    @GetMapping("/admin/done")
    public String adminCheckDone(Map<String,List<MatchItem>> map){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(Roles.ADMIN.getName())){
            return UNAUTH;
        }
        List<Pass> passes = passService.selectByUserId(hostHolder.getUser().getId());
        //将已审核的作品的id显示出来
        List<MatchItem> matchItems = passes.stream().map(e->{
            return matchItemService.selectByPrimaryKey(e.getMatchItemId());
        }).collect(Collectors.toList());
        map.put("matchItem",matchItems);
        return ADMIN_CHECKED;
    }
}
