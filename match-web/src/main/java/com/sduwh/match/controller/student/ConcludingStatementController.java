package com.sduwh.match.controller.student;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.MiddleCheckStage;
import com.sduwh.match.enums.TransferMemberStage;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.middlecheck.MiddleCheckService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.transfermember.TransferMemberService;
import com.sduwh.match.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-11.
 * 阶梯检查阶段
 */
@Controller
@RequestMapping("/student")
public class ConcludingStatementController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ConcludingStatementController.class);
    private static final String CONCLUDING_STATEMENT = "/student/concluding_statement_items";
    private static final String CONCLUDING_STATEMENT_ALTER = "/student/concluding_statement_alter";
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    StageService stageService;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    UserService userService;
    @Autowired
    TransferMemberService transferMemberService;

    /** 获取处于中期检查阶段，并且是个人参加的比赛*/
    @GetMapping("/match/concluding")
    public String getMiddle(Model model){
        //获取个人参加的所有比赛
        List<PersonalMatchInfo> personalMatchInfos = personalMatchInfoService.selectByUserId(hostHolder.getUser().getId());
        List<MatchItem> res = personalMatchInfos
                .stream()
                .map(e->matchItemService.selectByPrimaryKey(e.getMatchItemId()))
                .filter(item->{
                    //过滤掉已经过期的比赛
                    MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(item.getMatchInfoId());
                    return !matchInfoService.checkEnd(matchInfo);
                })
                .filter(item->{
                    //过滤掉是中期检查阶段的比赛
                    if(item.getNowStageId() < 0)return false;
                    Stage stage = stageService.selectByPrimaryKey(item.getNowStageId());
                    return stage.getStageFlag() == MatchStage.CONCLUSION_CHECK.getId();
                })
                .filter(item->{
                    //过滤掉已经提交的阶段
                    ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(item.getId());
                    if(concludingStatement == null)return true;
                    if(concludingStatement.getStage() != ConcludingStatementStage.STUDENT_JUDGE.getCode())return false;
                    return true;
                })
                .collect(Collectors.toList());
        model.addAttribute("items",res);
        return CONCLUDING_STATEMENT;
    }

    /** 跳转到某个比赛的结题检查报告页面，如果之前上传过，就把页面中的一些值更改一下*/
    @GetMapping("/match/concluding/report/{id}")
    public String middleReport(@PathVariable("id")int matchItemId,Model model){
        ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(matchItemId);
        model.addAttribute("matchItemId",matchItemId);
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        model.addAttribute("info",matchInfo);
        List<User> users = Arrays.stream(matchItem.getMemberIds().split(",")).map(id->{
            //通过id查找User
            return userService.selectByPrimaryKey(Integer.parseInt(id));
        }).collect(Collectors.toList());
        model.addAttribute("members",users);
        model.addAttribute("nowMembers",matchItem.getMemberIds());

        if(concludingStatement == null){
            model.addAttribute("isNull","true");
        }else model.addAttribute("concludingStatement",concludingStatement);
        return CONCLUDING_STATEMENT_ALTER;
    }


    /** 更新结题检查报告表*/
    @PostMapping("/match/concluding/report/update")
    @ResponseBody
    public String updateReport(ConcludingStatement concludingStatement){
        //TODO 首先查看当前middelCheck是否合法,当前用户是否参加了该比赛，该比赛是否到达了该阶段

        ConcludingStatement  tmp = concludingStatementService.selectByMatchItemId(concludingStatement.getMatchItemId());
        if(tmp != null){
            if(tmp.getStage() != ConcludingStatementStage.STUDENT_JUDGE.getCode()){
                //当前不是学生评价阶段
                return setJsonResult("error","当前非学生评价阶段");
            }
            concludingStatement.setStage(MiddleCheckStage.STUDENT_JUDGE.getCode());
            //注意，对于int类型的，默认是0
            concludingStatementService.updateByMatchItemIdSelective(concludingStatement);
        }else{
            concludingStatement.setStage(MiddleCheckStage.STUDENT_JUDGE.getCode());
            concludingStatementService.insert(concludingStatement);
        }
        return setJsonResult("success","true");
    }

    /** 学生结题检查日志编写完毕，提交到下一个阶段*/
    @PostMapping("/match/concluding/report/submit")
    @ResponseBody
    public String submit(@RequestParam("matchItemId") int matchItemId,
                         @RequestParam("memberIds[]")String memberIds,
                         @RequestParam("isChange")String isChange){
        //TODO 首先查看当前middelCheck是否合法,当前用户是否参加了该比赛，该比赛是否到达了该阶段
        ConcludingStatement  tmp = concludingStatementService.selectByMatchItemId(matchItemId);
        if(tmp != null && tmp.getStage() == ConcludingStatementStage.STUDENT_JUDGE.getCode()){
            tmp.setStage(MiddleCheckStage.TEACHER_JUDGE.getCode());
            concludingStatementService.updateByMatchItemIdSelective(tmp);

            //将来待审核的阶段保存在redis，等待老师取出审核
            //使用set保存
            //首先获取该比赛的teacherid
            MatchItem matchItem = matchItemService.selectByPrimaryKey(tmp.getMatchItemId());
            Arrays.stream(matchItem.getTeacherIds().split(","))
                    .forEach(id->{
                        //针对每一个id都将其保存在redis中
                        String key = RedisKeyGenerator.getTeacherConcludingCheckKey(Integer.parseInt(id));
                        jedisAdapter.sadd(key, String.valueOf(tmp.getMatchItemId()));
                    });

            //变更成员
            if(Integer.valueOf(isChange) == 1){
                //如果isChange是1,表明需要变更成员
                TransferMember transferMember = new TransferMember();
                transferMember.setCreateTime(new Date());
                transferMember.setFromMembers(matchItem.getMemberIds());
                transferMember.setToMembers(memberIds);
                transferMember.setMatchItemId(matchItemId);
                transferMember.setStage(TransferMemberStage.TEACHER_CHECK.getCode());
                transferMemberService.insert(transferMember);

                //千万不要更新成员，审核通过才能更新。。。。
            }
            return setJsonResult("success","true");
        }
        return setJsonResult("error","提交失败");
    }
}
