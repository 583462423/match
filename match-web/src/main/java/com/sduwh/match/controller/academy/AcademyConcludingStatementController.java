package com.sduwh.match.controller.academy;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.enums.MiddleCheckStage;
import com.sduwh.match.enums.TransferMemberStage;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
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
@RequestMapping("/academy")
public class AcademyConcludingStatementController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AcademyConcludingStatementController.class);
    private static final String ACADEMY_CONCLUDING_CHECK = "/academy/concluding_check_items";
    private static final String ACADEMY_CONCLUDING_CHECK_ALTER = "/academy/concluding_check_alter";

    @Autowired
    MatchItemService matchItemService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    UserService userService;
    @Autowired
    TransferMemberService transferMemberService;
    /** 获取处于中期检查阶段*/
    @GetMapping("/match/concluding")
    public String getMiddle(Model model){
        //从redis中获取待审核的ConcludingStatement的matchItemId
        String key = RedisKeyGenerator.getTeacherConcludingCheckKey(hostHolder.getUser().getId());
        Set<String> sets = jedisAdapter.sget(key);
        //根据每个id来获取对应的比赛，将其显示出来
        List<MatchItem> list = sets.stream().map(id->{
            MatchItem matchItem = matchItemService.selectByPrimaryKey(Integer.parseInt(id));
            return matchItem;
        }).collect(Collectors.toList());

        model.addAttribute("items",list);
        return ACADEMY_CONCLUDING_CHECK;
    }

    @GetMapping("/match/concluding/report/{id}")
    public String getMiddleCheck(@PathVariable("id") int matchItemId,
                                 Model model){
        ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(matchItemId);
        model.addAttribute("concludingStatement",concludingStatement);

        //获取成员变更表,第一个是最晚创建的，将最晚创建的放到model中
        List<TransferMember> transferMembers = transferMemberService.selectByMatchItemId(matchItemId).stream()
                .filter(item->
                        //过滤不是教师评价阶段
                        item.getStage() == TransferMemberStage.ACADEMY_CHECK.getCode())
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

        return ACADEMY_CONCLUDING_CHECK_ALTER;
    }

    /** 更新学院评价*/
    @PostMapping("/match/concluding/report/update")
    @ResponseBody
    public String updateTeacherReport(ConcludingStatement concludingStatement){
        //获取到middleCheck
        ConcludingStatement  tmp = concludingStatementService.selectByMatchItemId(concludingStatement.getMatchItemId());
        if(tmp == null)return setJsonResult("error","未授权");
        if(tmp.getStage() != ConcludingStatementStage.ACADEMY_JUDGE.getCode()){
            return setJsonResult("error","当前非学院评价阶段");
        }

        concludingStatement.setStage(ConcludingStatementStage.ACADEMY_JUDGE.getCode());
        //注意，对于int类型的，默认是0
        concludingStatementService.updateByMatchItemIdSelective(concludingStatement);
        return setJsonResult("success","true");
    }

    /** 提交到下一个阶段*/
    @PostMapping("/match/concluding/report/submit")
    @ResponseBody
    public String submit(@RequestParam("matchItemId") int matchItemId){
        //TODO 首先查看当前middelCheck是否合法,当前用户是否参加了该比赛，该比赛是否到达了该阶段

        //提交到下一个阶段是学校审核，一股脑的提交就性了
        User superUser = userService.selectSuperUser();

        if(superUser == null)return setJsonResult("error","学校管理员为空");
        ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(matchItemId);
        if(concludingStatement == null)return setJsonResult("error","无此比赛");
        concludingStatement.setStage(MiddleCheckStage.SUPER_JUDGE.getCode());
        concludingStatementService.updateByMatchItemIdSelective(concludingStatement);
        //当前阶段移除
        jedisAdapter.srem(RedisKeyGenerator.getTeacherConcludingCheckKey(hostHolder.getUser().getId()),String.valueOf(matchItemId));
        //将该审核的比赛id加到学院管理员的redis中
        jedisAdapter.sadd(RedisKeyGenerator.getTeacherConcludingCheckKey(superUser.getId()),String.valueOf(matchItemId));
        return setJsonResult("success","true");
    }


    /** 成员变更允许，通过学院审核审核阶段*/
    @PostMapping("/match/concluding/change/submit")
    @ResponseBody
    public String passChange(@RequestParam("transferMemberId")int transferMemberId){
        TransferMember transferMember = transferMemberService.selectByPrimaryKey(transferMemberId);
        transferMember.setStage(TransferMemberStage.SUPER_CHECK.getCode());
        transferMemberService.updateByPrimaryKeySelective(transferMember);
        return setJsonResult("success","true");
    }
}
