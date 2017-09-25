package com.sduwh.match.controller.admin;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.exception.base.BaseException;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.wrapper.MatchItemWithScore;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-22.
 * 评奖的控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminAwardsController extends BaseController {
    private static final String ADMIN_AWARDS = "/admin/awards";

    @Autowired
    StageService stageService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    GradeService gradeService;
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 获取info对应的 matchItem需要评奖的比赛，并且把分数也带进去
     */
    @GetMapping("/awards/info/{id}")
    public String getMatchItemsOfAwards(@PathVariable("id") int matchInfoId, Model model) {
        //判断当前阶段是否已经结束
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchInfoId);
        if (!matchInfoService.checkIsRunning(matchInfo))
            throw new BaseException("该比赛已经结束或未开始！");

        //获取处于学校评奖阶段的比赛
        List<MatchItemWithScore> ms = matchItemService.selectAwardsWithScore(matchInfoId);
        model.addAttribute("matchItemWithScore", ms);
        model.addAttribute("matchInfoId", matchInfoId);
        return ADMIN_AWARDS;
    }

    /**
     * 根据分数进行评奖
     */
    @PostMapping("/awards/award")
    @ResponseBody
    public String awardToSomeMatchItems(@RequestParam("num") int num,
                                        @RequestParam("matchInfoId") int matchInfoId) {
        //将获奖成员加入到redis中，然后将当前状态设置为下一个状态
        String key = RedisKeyGenerator.getSuperAwardKey(matchInfoId);
        List<MatchItemWithScore> ms = matchItemService.selectAwardsWithScore(matchInfoId);
        if (ms.size() < num) return setJsonResult("error", "数量过大，超过当前的比赛的数量");
        ms.stream().limit(num).forEach(m -> {
            jedisAdapter.zadd(key, m.getScore(), String.valueOf(m.getMatchItem().getId()));
        });
        //设置下一个状态,将所有的matchInfoId对应的比赛都设置为下一个阶段，因为都已经评奖完毕了，所以不能再有其他阶段了= =
        matchItemService.selectAllByInfoId(matchInfoId).stream().forEach(matchItem -> {
            matchItemService.updateAndSetNextStage(matchItem, null);
        });

        return setJsonResult("success", "true");
    }

}
