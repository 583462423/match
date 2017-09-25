package com.sduwh.match.controller.student;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.researchlog.ResearchLogService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.util.TimestampFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-7.
 * 科研日志阶段
 */
@Controller
@RequestMapping("/student")
public class ScienceLogController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ScienceLogController.class);

    private static final String SCIENCE_MATCH = "/student/science_match";
    private static final String SCIENCE_LOG_ALL = "/student/scienceLogDetail";

    @Autowired
    HostHolder hostHolder;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    StageService stageService;
    @Autowired
    ResearchLogService researchLogService;
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;

    /** 获取处于科研日志阶段的比赛，并且是当前用户参与的比赛*/
    @GetMapping("/match/science")
    public String getScienceStageMatch(Model model){
        User user = hostHolder.getUser();
        List<PersonalMatchInfo> personalMatchInfoList = personalMatchInfoService.selectByUserId(user.getId());
        List<MatchItem> matchItems = personalMatchInfoList.stream()
                .map(p->matchItemService.selectByPrimaryKey(p.getMatchItemId()))
                .filter(item->{
                    //将不在科研日志阶段的比赛过滤掉
                    //注意判断stageId是否小于0,小于0的都是非正常结束状态
                    if(item.getNowStageId() < 0)return false;
                    Stage stage = stageService.selectByPrimaryKey(item.getNowStageId());
                    return stage.getStageFlag() == MatchStage.RESEARCH_LOG.getId();
                }).collect(Collectors.toList());

        model.addAttribute("items",matchItems);
        return SCIENCE_MATCH;
    }

    /** 给某个比赛添加科研日志*/
    @PostMapping("/science/log/add/{id}")
    @ResponseBody
    public String addScienceLog(@RequestParam("title")String title,
                                @RequestParam("content")String content,
                                @RequestParam("time")String time,
                                @RequestParam("startTime")int startTime,
                                @RequestParam("endTime")int endTime,
                                @PathVariable("id")int matchItemId){
        //首先判断这个比赛阶段是否是科研日志阶段
        System.out.println(time);
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.RESEARCH_LOG.getId())
            throw new IllegalArgumentException("只有科研日志阶段才能添加日志！");
        //接着判断，该用户是否参加了该比赛
        if(!personalMatchInfoService.selectByUserId(hostHolder.getUser().getId()).stream().anyMatch(p->{
            return p.getMatchItemId() == matchItemId;
        }))throw new IllegalArgumentException("您没有参加该类型的比赛，不能添加日志");

        //符合条件后，可以添加日志
        ResearchLog researchLog = new ResearchLog();

        researchLog.setTime(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd",time));
        researchLog.setStartTime(startTime);
        researchLog.setEndTime(endTime);
        researchLog.setContent(content);
        researchLog.setMatchItemId(matchItemId);
        researchLog.setTitle(title);
        researchLog.setLastTime(new Date());
        researchLogService.insert(researchLog);


        return setJsonResult("success","true");
    }

    /** 查询某个比赛的所有日志信息*/
    @GetMapping("/science/all/{id}")
    public String getAllScience(@PathVariable("id")int matchItemId,
                                Model model){

        //TODO 判断，当前用户，是否参与的这个比赛
        List<ResearchLog> list = researchLogService.getAllLogByMatchItemId(matchItemId);
        model.addAttribute("logs",list);
        return SCIENCE_LOG_ALL;
    }

    /** 获取单个日志的详细信息*/
    @GetMapping("/science/detail/{id}")
    @ResponseBody
    public String getScienceDetailById(@PathVariable("id") int id){
        return JSONObject.toJSONString(researchLogService.selectByPrimaryKey(id));
    }

    @PostMapping("/science/detail/update")
    @ResponseBody
    public String updateScienceDetailById(@RequestParam("id")int id,
                                          @RequestParam("title")String title,
                                          @RequestParam("content")String content,
                                          @RequestParam("time")String time,
                                          @RequestParam("startTime")int startTime,
                                          @RequestParam("endTime")int endTime){
        //更新ResearchLog
        ResearchLog researchLog = new ResearchLog();
        researchLog.setId(id);
        researchLog.setTitle(title);
        researchLog.setContent(content);
        researchLog.setTime(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd",time));
        researchLog.setStartTime(startTime);
        researchLog.setEndTime(endTime);
        researchLogService.updateByPrimaryKeySelective(researchLog);
        return setJsonResult("success","true");
    }
}
