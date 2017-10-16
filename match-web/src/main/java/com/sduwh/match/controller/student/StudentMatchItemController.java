package com.sduwh.match.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-7-21.
 * 学生比赛管理器，主要包括比赛创建，比赛展示等信息
 */
@Controller
@RequestMapping("/student")
public class StudentMatchItemController extends BaseController {

    private static final String STUDENT_MATCHS = "/student/matchs";
    private static final String STUDENT_MATCH_APPLY = "/student/applyMatch";
    private static final String UP_APPLY = "/student/upApply";
    private static final String MY_MATCH = "/student/my_match";
    private static final Logger logger = LoggerFactory.getLogger(StudentMatchItemController.class);


    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    UserService userService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    StageService stageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;
    @Autowired
    ApplyService applyService;
    /** 显示所有可以参加的比赛*/
    @GetMapping("/matchs")
    public String getMatch(Map<String,List<MatchInfo>> map){
        List<MatchInfo> matchInfos = matchInfoService.selectAll()
                .stream()
                .filter(e->{
                    //找出来在现在日期之前的
                    return matchInfoService.checkIsRunning(e);
                })
                .filter(e->{
                    //过滤掉当前不在申请阶段的
                    return Arrays.stream(e.getAllStage().split(",")).anyMatch(el->{
                        int stageId = Integer.parseInt(el);
                        Stage stage = stageService.selectByPrimaryKey(stageId);
                        if(stage.getStageFlag() == MatchStage.APPLY.getId()){
                            //判断当前日期是否已经结束
                            return stageService.checkIsRuning(stage);
                        }
                        return false;
                    });
                })
                .collect(Collectors.toList());
        map.put("info",matchInfos);
        return STUDENT_MATCHS;
    }

    //申请比赛，但是只能是队长申请,id是对应的比赛名称
    @GetMapping("/matchs/apply/{id}")
    public ModelAndView apply(@PathVariable("id") Integer id){
        //获取id对应的比赛
        //注意也要把队长的信息放进去，谁申请的谁就是队长
        Subject currentSubject = SecurityUtils.getSubject();
        String username = (String) currentSubject.getPrincipal();
        User user = userService.selectByUsername(username);

        ModelAndView mav = new ModelAndView(STUDENT_MATCH_APPLY);
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(id);
        mav.addObject("info",matchInfo);
        mav.addObject("leader",user);
        return mav;
    }

    /** 上传申请表阶段*/
    @GetMapping("/matchs/upapply/{id}")
    public String upApply(Model model,@PathVariable("id") int id){
        //获取id对应的matchItem信息
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.APPLY.getId()){
            //如果不是立项申请阶段balabala
            //TODO ...= =好多TODO的任务唉。如果当前不需要上传，那么就跳转到不能上传的信息的页面。
            return UNAUTH;
        }

        if(matchItem.getApplyId() != null){
            Apply apply = applyService.selectByPrimaryKey(matchItem.getApplyId());
            model.addAttribute("apply",apply.getName());
        }

        //准备开始上传申请表了。。。
        model.addAttribute("item",matchItem);
        return UP_APPLY;
    }

    /** 确认申请表的提交*/
    @GetMapping("/match/apply/submit/{itemId}")
    @ResponseBody
    public String confirmSubmitApply(@PathVariable("itemId") int id){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        Map<String,String> info = new HashMap<>();
        if(matchItem.getApplyId() == null){
            info.put("error","请上传申请表后再进行提交!");
            return JSONObject.toJSONString(info);
        }
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        String[] stages = matchInfo.getAllStage().split(",");
        int nextStageId = -1;
        for(int i=0; i<stages.length; i++){
            int tmp = Integer.parseInt(stages[i]);
            if(matchItem.getNowStageId() == tmp && i!=stages.length-1){
                //判断当前是否是申请阶段
                if(stageService.selectByPrimaryKey(matchItem.getNowStageId()).getStageFlag() == MatchStage.APPLY.getId()){
                    nextStageId = Integer.parseInt(stages[i+1]);
                    break;
                }else{
                    info.put("error","已提交过！不能重复提交！");
                    return JSONObject.toJSONString(info);
                }
            }
        }
        if(nextStageId == -1){
            //说明整个流程都结束了。。。。那你想怎么样？，那就把-1都设置进去，就表示结束,alldone。。
        }
        matchItem.setNowStageId(nextStageId);
        matchItemService.updateByPrimaryKeySelective(matchItem);
        info.put("success","true");
        return JSONObject.toJSONString(info);
    }

    /** 当前参与的比赛*/
    @GetMapping({"/match/my"})
    public String getMyMathc(Map<String,List<Map<String,String>>> res){
        //显示当前的比赛，信息有比赛的名称，比赛的分工，比赛当前到哪个阶段了，比赛是否过期等信息
        List<PersonalMatchInfo> personalMatchInfo = personalMatchInfoService.selectByUserId(hostHolder.getUser().getId());
        List<Map<String,String>> list = new ArrayList<>();
        personalMatchInfo.forEach(e->{
            Map<String,String> map = new HashMap<>();
            MatchItem matchItem = matchItemService.selectByPrimaryKey(e.getMatchItemId());
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
            Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
            //名称
            map.put("title",matchItem.getTitle());
            //分工
            map.put("job",e.getJobAssignment());
            map.put("itemId",String.valueOf(matchItem.getId()));
            //状态
            MatchStage[] stages = MatchStage.values();
            map.put("stage","所有阶段均已完成");
            if(stage != null){
                for(int i=0; i<stages.length; i++){
                    if(stage.getStageFlag() == stages[i].getId()){
                        if (stage.getStageFlag() == MatchStage.APPLY.getId()) {
                            //如果是立项申请，就需要显示跳转立项申请页面
                            map.put("upApply","true");
                        }
                        if(stage.getStageFlag() == MatchStage.END_SHOW.getId() && stageService.checkIsRuning(stage)){
                            //当前结果公布
                            map.put("award","true");
                            map.put("infoid",String.valueOf(matchInfo.getId()));
                        }
                        //判断当前stage是否开始
                        if(stageService.checkIsRuning(stage))map.put("stage",stages[i].getDes() + ":已开始");
                        else map.put("stage",stages[i].getDes() + ":未开始");
                        break;
                    }
                }
            }
            //比赛是否过期
            if(matchInfo.getEndTime().before(new Date())){
                map.put("expire","已过期");
            }else{
                map.put("expire","正在运行中");
            }
            list.add(map);
        });
        res.put("res",list);
        return MY_MATCH;
    }

}
