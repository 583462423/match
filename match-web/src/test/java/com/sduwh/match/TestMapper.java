package com.sduwh.match;


import com.sduwh.match.dao.MatchInfoMapper;
import com.sduwh.match.dao.MatchTypeMapper;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.model.wrapper.RoleTOWrapper;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.matchtype2.MatchType2Service;
import com.sduwh.match.service.permission.PermissionService;
import com.sduwh.match.service.role.RoleService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
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
    MatchItemService matchItemService;

    @Test
    public void test(){
        System.out.println(UUID.randomUUID());
    }
}
