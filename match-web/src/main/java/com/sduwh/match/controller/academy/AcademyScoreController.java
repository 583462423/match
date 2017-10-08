package com.sduwh.match.controller.academy;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.model.to.MatchItemTO;
import com.sduwh.match.model.to.ScoreInfo;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-17.
 * 学院管理员进行评分的管理器
 */
@Controller
@RequestMapping("/academy")
public class AcademyScoreController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AcademyScoreController.class);
    private static final String SCORE_ALL = "/academy/score_all";
    private static final String SCORE_CREATE_RATER = "/academy/score_items";
    @Autowired
    StageService stageService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    GradeService gradeService;
    @Autowired
    UserService userService;

    /** 获取当前处于评分阶段的所有的比赛*/
    @GetMapping("/match/score/all")
    public String getMatchScoreStage(Model model){
        //获取当前处于评分阶段的matchInfo
        List<Stage> stageList = stageService.selectByStageFlag(MatchStage.CONCLUSION_CHECK.getId());
        List<MatchInfo> matchInfos = stageList.stream().flatMap( stage-> matchItemService.selectByNowStageId(stage.getId()).stream())
                .filter(item->{
                    //过滤当前concludingStatement不是处于评分阶段的比赛
                    ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(item.getId());
                    return concludingStatement.getStage() == ConcludingStatementStage.ACADEMY_SCORE.getCode();
                }).map(matchItem -> matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId()))
                .distinct().collect(Collectors.toList());

        model.addAttribute("infos",matchInfos);
        return SCORE_ALL;
    }

    @GetMapping("/score/info/{id}")
    public String getScoreByMatchInfoId(@PathVariable("id") int matchInfoId,Model model){
        //通过matchInfoId,来获取这个比赛中的待评审的所有比赛
        String key = RedisKeyGenerator.getAcademyScoreKey(hostHolder.getUser().getId(),matchInfoId);
        System.out.println(key);
        ScoreInfo scoreInfo = gradeService.getScoreInfo(RaterLevel.ACADEMY,key,matchInfoId,ConcludingStatementStage.ACADEMY_SCORE);
        model.addAttribute("matchInfoId",scoreInfo.getMatchInfoId());
        model.addAttribute("itemTOS", scoreInfo.getMatchItemTOS());
        model.addAttribute("cnt",scoreInfo.getCnt());
        model.addAttribute("nowCnt",scoreInfo.getNowCnt());
        model.addAttribute("last",scoreInfo.getLast());
        model.addAttribute("notDoneCnt",scoreInfo.getNotDoneCnt());
        return SCORE_CREATE_RATER;
    }

    /** 评分阶段结束*/
    @PostMapping("/rater/score/end")
    @ResponseBody
    public synchronized String endScore(@RequestParam("infoId")int infoId){
        String key = RedisKeyGenerator.getAcademyScoreKey(hostHolder.getUser().getId(),infoId);
        User superUser = userService.selectSuperUser();
        //获取到该阶段的所有比赛，然后将对应的结题检查报告都设置到某某阶段
        String superKey = RedisKeyGenerator.getSuperScoreKey(superUser.getId(),infoId);
        if(jedisAdapter.sget(key) == null || jedisAdapter.sget(key).size() == 0)return setJsonResult("error","该比赛没有可设置的比赛");
        jedisAdapter.sget(key).stream().map(Integer::parseInt).map(concludingStatementService::selectByMatchItemId).forEach(cs->{
            //将每个cs都设置为下一个阶段
            cs.setStage(ConcludingStatementStage.SUPER_SCORE.getCode());
            concludingStatementService.updateByMatchItemId(cs);
            //将该比赛记录从当前比赛中移除
            jedisAdapter.srem(key,String.valueOf(cs.getMatchItemId()));
            jedisAdapter.sadd(superKey,String.valueOf(cs.getMatchItemId()));
        });
        return setJsonResult("success","true");
    }

    @PostMapping("/rater/gen")
    @ResponseBody
    public String genRater(@RequestParam("matchItem") String matchItems,
                           @RequestParam("startTime") String startTime,
                           @RequestParam("endTime") String endTime,
                           @RequestParam("cnt") Integer cnt,
                           @RequestParam("matchInfoId") int matchInfoId) throws ParseException {
        //给这些比赛生成临时评委
        //判断是否有选项是空的
        if(StringUtils.nullOrEmpty(matchItems,startTime,endTime,String.valueOf(cnt),String.valueOf(matchInfoId)))
            return setJsonResult("error","输入内容不得为空!");
        List<TmpRater> result = tmpRaterService.createRater(matchItems,startTime,endTime,cnt, RaterLevel.ACADEMY.getLevel(),matchInfoId);
        //将来生成的评委的帐号密码生成字符串
        StringBuilder ss = new StringBuilder();
        result.forEach(t->{
            ss.append("帐号:").append(t.getUsername()).append("    密码:").append(t.getPassword()).append("\n");
        });
        return setJsonResult("success","true","raterInfo",ss.toString());
    }
}
