package com.sduwh.match.controller.teacher;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MiddleCheckStage;
import com.sduwh.match.enums.Roles;
import com.sduwh.match.enums.TransferMemberStage;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.MiddleCheck;
import com.sduwh.match.model.entity.TransferMember;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.middlecheck.MiddleCheckService;
import com.sduwh.match.service.transfermember.TransferMemberService;
import com.sduwh.match.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-14.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherMiddleCheckController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(TeacherMiddleCheckController.class);
    private static final String TEACHER_MIDDLE_CHECK = "/teacher/middle_check_items";
    private static final String TEACHER_MIDDLE_CHECK_ALTER = "/teacher/middle_check_alter";

    @Autowired
    MatchItemService matchItemService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    MiddleCheckService middleCheckService;
    @Autowired
    UserService userService;
    @Autowired
    TransferMemberService transferMemberService;
    /** 获取处于中期检查阶段*/
    @GetMapping("/match/middle")
    public String getMiddle(Model model){
        //从redis中获取待审核的MiddleCheck的matchItemId
        String key = RedisKeyGenerator.getTeacherMiddleCheckKey(hostHolder.getUser().getId());
        Set<String> sets = jedisAdapter.sget(key);
        //根据每个id来获取对应的比赛，将其显示出来
        List<MatchItem> list = sets.stream().map(id->{
            MatchItem matchItem = matchItemService.selectByPrimaryKey(Integer.parseInt(id));
            return matchItem;
        }).collect(Collectors.toList());

        model.addAttribute("items",list);
        return TEACHER_MIDDLE_CHECK;
    }

    @GetMapping("/match/middle/report/{id}")
    public String getMiddleCheck(@PathVariable("id") int matchItemId,
                                 Model model){
        //获取matchItemId对应的MiddleCheck
        MiddleCheck middleCheck = middleCheckService.selectByMatchItemId(matchItemId);
        //获取成员变更表,第一个是最晚创建的，将最晚创建的放到model中
        List<TransferMember> transferMembers = transferMemberService.selectByMatchItemId(matchItemId).stream()
                .filter(item->
                        //过滤不是教师评价阶段
                    item.getStage() == TransferMemberStage.TEACHER_CHECK.getCode())
                .sorted((o1, o2) -> {
            if(o1.getCreateTime().before(o2.getCreateTime()))return -1;
            else return 1;
        }).collect(Collectors.toList());

        if(transferMembers.size() == 0){
            model.addAttribute("isChange","0");
        }else{
            TransferMember transferMember = transferMembers.get(0);
            model.addAttribute("isChange","1");
            model.addAttribute("transferMemberId",transferMember.getId());
            List<User> fromMembers = Arrays.stream(transferMember.getFromMembers().split(",")).map(Integer::parseInt).map(userService::selectByPrimaryKey).collect(Collectors.toList());
            List<User> toMembers = Arrays.stream(transferMember.getToMembers().split(",")).map(Integer::parseInt).map(userService::selectByPrimaryKey).collect(Collectors.toList());
            model.addAttribute("fromMembers",fromMembers);
            model.addAttribute("toMembers",toMembers);
        }
        model.addAttribute("middleCheck",middleCheck);
        return TEACHER_MIDDLE_CHECK_ALTER;
    }

    /** 更新教师评价*/
    @PostMapping("/match/middle/report/update")
    @ResponseBody
    public String updateTeacherReport(MiddleCheck middleCheck){
        //获取到middleCheck
        MiddleCheck  tmp = middleCheckService.selectByMatchItemId(middleCheck.getMatchItemId());
        if(tmp == null)return setJsonResult("error","未授权");
        if(tmp.getStage() != MiddleCheckStage.TEACHER_JUDGE.getCode()){
            return setJsonResult("error","当前非教师评价阶段");
        }

        middleCheck.setStage(MiddleCheckStage.TEACHER_JUDGE.getCode());
        //注意，对于int类型的，默认是0
        middleCheckService.updateByMatchItemIdSelective(middleCheck);
        return setJsonResult("success","true");
    }

    /** 提交到下一个阶段*/
    @PostMapping("/match/middle/report/submit")
    @ResponseBody
    public String submit(@RequestParam("matchItemId") int matchItemId){
        //TODO 首先查看当前middelCheck是否合法,当前用户是否参加了该比赛，该比赛是否到达了该阶段
        // 提交到下一个阶段的前提是所有的指导老师都已经审核完毕，需要进行判断
        //提交到下一个阶段是学院审核，首先要找到该比赛的队长对应的学院，然后找到学院管理员，将其保存在redis中,并在当前阶段移除
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        boolean flag = Arrays.stream(matchItem.getTeacherIds().split(","))
                .map(Integer::parseInt)
                .filter(id->{
                    //将当前的教师的id清除
                    return hostHolder.getUser().getId() != id;
                })
                .anyMatch(id->{
                    //只要在redis存在任何一个id，那么就认为没有通过
                    String key = RedisKeyGenerator.getTeacherMiddleCheckKey(id);
                    return jedisAdapter.sget(key).contains(matchItem.getId());
                });
        //限定leaderids只能为一个，所以不需要分割
        int leaderId = Integer.parseInt(matchItem.getLeaderIds());
        User user = userService.selectByPrimaryKey(leaderId);
        //获取学院管理员
        User academyUser = userService.selectAcademyUserByAcademyId(Roles.ACADEMY_ADMIN.getId(),user.getAcademyId());

        if(academyUser == null)return setJsonResult("error","学院管理员为空");
        MiddleCheck middleCheck = middleCheckService.selectByMatchItemId(matchItemId);
        if(middleCheck == null)return setJsonResult("error","无此比赛");
        //当前阶段移除
        jedisAdapter.srem(RedisKeyGenerator.getTeacherMiddleCheckKey(hostHolder.getUser().getId()),String.valueOf(matchItemId));
        //将该审核的比赛id加到学院管理员的redis中
        if(!flag){
            //flag为true的时候，表明所有的指导老师没有审核完毕，所以需要等待所有指导老师审核完毕。
            middleCheck.setStage(MiddleCheckStage.ACADEMY_JUDGE.getCode());
            middleCheckService.updateByMatchItemIdSelective(middleCheck);
            jedisAdapter.sadd(RedisKeyGenerator.getTeacherMiddleCheckKey(academyUser.getId()),String.valueOf(matchItemId));
        }
        return setJsonResult("success","true");
    }


    /** 成员变更允许，通过教师审核阶段*/
    @PostMapping("/match/middle/change/submit")
    @ResponseBody
    public String passChange(@RequestParam("transferMemberId")int transferMemberId){
        TransferMember transferMember = transferMemberService.selectByPrimaryKey(transferMemberId);
        transferMember.setStage(TransferMemberStage.ACADEMY_CHECK.getCode());
        transferMemberService.updateByPrimaryKeySelective(transferMember);
        return setJsonResult("success","true");
    }
}
