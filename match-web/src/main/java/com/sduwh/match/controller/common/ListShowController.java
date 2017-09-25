package com.sduwh.match.controller.common;

import com.sduwh.match.async.producer.ListShowProducer;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Created by qxg on 17-9-6.
 * 名单公布，这个地方不需要权限，除非你想要权限 = =
 * 说明的是，公布的规则是，当前的状态是名单公布，如果没有到达该状态，说明其他状态被阻止，也就是没通过，并且在某个时间到的时候，也会进行公布
 */
@Controller
public class ListShowController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ListShowProducer.class);
    private static final String MATCH_LIST_SHOW = "/public/matchShow";
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    StageService stageService;

    /** 某个比赛的名单公布*/
    @GetMapping("/match/show/{stageId}")
    public String getListShow(@PathVariable("stageId") int stageId,
                              Model model){
        //首先获取stageId,查看Stage是否符合条件
        Stage stage = stageService.selectByPrimaryKey(stageId);
        Date now = new Date();
        if(stage.getStartTime().after(now) || stage.getEndTime().before(now))throw new IllegalArgumentException("当前stage没有开放");

        //如果符合状态，就查找对应的matchItem
        List<MatchItem> list = matchItemService.selectByNowStageId(stageId);
        model.addAttribute("list",list);
        return MATCH_LIST_SHOW;
    }
}
