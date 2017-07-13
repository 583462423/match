package com.sduwh.match.controller.admin;

import com.sduwh.match.base.BaseController;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.wrapper.RaterWrapper;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.role.RoleService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by qxg on 17-7-12.
 * 查看当前校级评委并生成校级评委
 */
@Controller
@RequestMapping("/admin/rater")
public class RaterController extends BaseController{

    private static final String RATER_ALL = "/admin/rater";
    private static final String RATER_GEN = "/admin/raterGen";

    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    MatchInfoService matchInfoService;

    /** 获取所有的评委信息*/
    @GetMapping("/all")
    public String getAll(Map<String, List<RaterWrapper>> map){
        map.put("wrappers",tmpRaterService.selectAll());
        return RATER_ALL;
    }


    /** 生成评委信息*/
    @GetMapping("/gen")
    public String gen(Map<String,List<MatchInfo>> map){
        //如果要生成评委信息，首先要选择哪一种比赛，所以先获取比赛info的信息，然后通过info再获取该种类型的所有比赛条目，再通过比赛日期等内容进行选择，要提供多选功能。
        map.put("info",matchInfoService.selectAll());
        return RATER_GEN;
    }
}
