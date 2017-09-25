package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.wrapper.MatchItemWithScore;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-22.
 */
@Controller
public class SuperRankController extends BaseController {
    private static final String SUPER_RANK = "/rank";

    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    GradeService gradeService;
    /**
     * 某个比赛的排名
     */
    @GetMapping("/awards/rank/{id}")
    public String rank(@PathVariable("id") int matchInfoId, Model model) {
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchInfoId);
        //获取比赛获奖名单
        String key = RedisKeyGenerator.getSuperAwardKey(matchInfoId);
        List<MatchItemWithScore> matchItemWithScores = jedisAdapter.zgetAll(key).stream().map(id -> {
            MatchItemWithScore ms = new MatchItemWithScore();
            MatchItem m = matchItemService.selectByPrimaryKey(Integer.parseInt(id));
            ms.setMatchItem(m);
            ms.setScore(gradeService.selectAverangeByMatchItem(m.getId()));
            return ms;
        }).collect(Collectors.toList());
        model.addAttribute("matchInfo",matchInfo);
        model.addAttribute("matchItemWithScores", matchItemWithScores);
        return SUPER_RANK;
    }
}
