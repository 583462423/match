package com.sduwh.match;


import com.sduwh.match.enums.*;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.model.to.MatchItemTO;
import com.sduwh.match.model.wrapper.MatchItemWithScore;
import com.sduwh.match.service.MailSender;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.middlecheck.MiddleCheckService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.researchlog.ResearchLogService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.service.transfermember.TransferMemberService;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.TimestampFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-context.xml"})
public class TestMapper {

    @Autowired
    MiddleCheckService middleCheckService;
    @Autowired
    UserService userService;
    @Autowired
    TransferMemberService transferMemberService;
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    StageService stageService;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    GradeService gradeService;
    @Autowired
    ApplyService applyService;
    @Autowired
    PassService passService;
    @Autowired
    ResearchLogService researchLogService;
    @Test
    public void test(){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(4);
        matchItem.setByTime(MatchByTimeEnum.NO.getCode());
        matchItemService.updateByPrimaryKeySelective(matchItem);
    }

    @Test
    public void testRedis(){
        String key = RedisKeyGenerator.getListShowHandleHasDoneKey();
        jedisAdapter.srem(key,"109");
    }
}
