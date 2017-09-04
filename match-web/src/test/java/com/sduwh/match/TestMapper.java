package com.sduwh.match;


import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.stage.StageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-context.xml"})
public class TestMapper {

    @Autowired
    MatchInfoService matchInfoService;

    @Autowired
    PersonalMatchInfoService personalMatchInfoService;

    @Autowired
    MatchItemService matchItemService;

    @Autowired
    StageService stageService;
    @Autowired
    PassService passService;
    @Test
    public void test(){
        List<Stage> stages = stageService.selectCheckedStageByStageFlag(MatchStage.ACADEMY_VERIFY.getId());
        //通过stages的id去查找哪个比赛是符合条件的，并且还需要判断这个比赛是否已经结束
        List<MatchItem> matchItems = stages.stream().flatMap(e->{
            return matchItemService.selectByStageId(e.getId()).stream();
        }).filter(item->{
            MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(item.getMatchInfoId());
            return matchInfo.getEndTime().after(new Date());
        }).filter(item->{
            //判断这个比赛是否是当前学院，首先要查找到学院ID
            return 10 == item.getAcademyId();
        }).filter(item->{
            //判断当前用户是否已经审核过该用户了,如果没审核过，那才符合条件！
            Pass pass = passService.selectByUserAndItem(7,item.getId());
            return !passService.checkPass(pass);
        }).collect(Collectors.toList());

    }
}
