package com.sduwh.match.controller.admin;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MiddleCheckStage;
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
@RequestMapping("/admin")
public class AdminMiddleCheckController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AdminMiddleCheckController.class);
    private static final String ADMIN_MIDDLE_CHECK = "/admin/middle_check_items";
    private static final String ADMIN_MIDDLE_CHECK_ALTER = "/admin/middle_check_alter";

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
        return ADMIN_MIDDLE_CHECK;
    }

    @GetMapping("/match/middle/report/{id}")
    public String getMiddleCheck(@PathVariable("id") int matchItemId,
                                 Model model){
        //获取matchItemId对应的MiddleCheck
        MiddleCheck middleCheck = middleCheckService.selectByMatchItemId(matchItemId);
        model.addAttribute("middleCheck",middleCheck);

        //获取成员变更表,第一个是最晚创建的，将最晚创建的放到model中
        List<TransferMember> transferMembers = transferMemberService.selectByMatchItemId(matchItemId).stream()
                .filter(item->
                        //过滤不是教师评价阶段
                        item.getStage() == TransferMemberStage.SUPER_CHECK.getCode())
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
        return ADMIN_MIDDLE_CHECK_ALTER;
    }

    /** 更新学院评价*/
    @PostMapping("/match/middle/report/update")
    @ResponseBody
    public String updateTeacherReport(MiddleCheck middleCheck){
        //获取到middleCheck
        MiddleCheck  tmp = middleCheckService.selectByMatchItemId(middleCheck.getMatchItemId());
        if(tmp == null)return setJsonResult("error","未授权");
        if(tmp.getStage() != MiddleCheckStage.SUPER_JUDGE.getCode()){
            return setJsonResult("error","当前非学院评价阶段");
        }

        middleCheck.setStage(MiddleCheckStage.SUPER_JUDGE.getCode());
        //注意，对于int类型的，默认是0
        middleCheckService.updateByMatchItemIdSelective(middleCheck);
        return setJsonResult("success","true");
    }

    /** 提交到下一个阶段*/
    @PostMapping("/match/middle/report/submit")
    @ResponseBody
    public String submit(@RequestParam("matchItemId") int matchItemId){
        //TODO 首先查看当前middelCheck是否合法,当前用户是否参加了该比赛，该比赛是否到达了该阶段

        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        //提交到下一个阶段是没有了，这个地方就要更新比赛中的下一个阶段了
        MiddleCheck middleCheck = middleCheckService.selectByMatchItemId(matchItemId);
        if(middleCheck == null)return setJsonResult("error","无此比赛");
        middleCheck.setStage(MiddleCheckStage.ALL_DONW.getCode());
        middleCheckService.updateByMatchItemIdSelective(middleCheck);
        //当前阶段移除
        jedisAdapter.srem(RedisKeyGenerator.getTeacherMiddleCheckKey(hostHolder.getUser().getId()),String.valueOf(matchItemId));
        //更新比赛到下一个阶段
        matchItemService.updateAndSetNextStage(matchItem,null);
        return setJsonResult("success","true");
    }

    /** 成员变更允许，通过学校审核审核阶段*/
    @PostMapping("/match/middle/change/submit")
    @ResponseBody
    public String passChange(@RequestParam("transferMemberId")int transferMemberId){
        TransferMember transferMember = transferMemberService.selectByPrimaryKey(transferMemberId);
        transferMember.setStage(TransferMemberStage.ALL_DONE.getCode());
        transferMemberService.updateByPrimaryKeySelective(transferMember);

        //更新现在的成员
        MatchItem matchItem = matchItemService.selectByPrimaryKey(transferMember.getMatchItemId());
        matchItem.setMemberIds(transferMember.getToMembers());
        matchItemService.updateByPrimaryKeySelective(matchItem);
        return setJsonResult("success","true");
    }
}
