package com.sduwh.match.controller.rater;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.exception.base.BaseException;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.util.StringUtils;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-19.
 */
@Controller
@RequestMapping("/rater")
public class GradeController extends BaseController {

    private static final String GRADE = "/rater/academy_grade";
    private static final String SUPER_GRADE = "/rater/super_grade";

    @Autowired
    HostHolder hostHolder;
    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    GradeService gradeService;
    @Autowired
    StageService stageService;

    @GetMapping("/academy/grade/{id}")
    public String gradeMathcItem(@PathVariable("id") int matchItemId, Model model) {
        //判断是否有权限访问
        checkAuth(matchItemId,RaterLevel.ACADEMY);

        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        model.addAttribute("item",matchItem);
        return GRADE;
    }

    @PostMapping("/academy/grade/submit")
    @ResponseBody
    public String gradeToMatchItem(Grade grade){
        checkAuth(grade.getMatchItemId(),RaterLevel.ACADEMY);
        if(StringUtils.nullOrEmpty(grade.getComment(),String.valueOf(grade.getScore())))
            return setJsonResult("error","输入不得为空");
        TmpRater rater = hostHolder.getRater();
        grade.setLevel(rater.getLevel());
        grade.setRaterId(rater.getId());

        //查看之前是否已经添加过一次
        List<Grade> grades = gradeService.selectByRaterIdAndMatchItemId(grade.getMatchItemId(),rater.getId());
        if(grades == null || grades.size() >= 1){
            //之前已经添加过，不需要添加
            return setJsonResult("error","您已经评价过该比赛，不允许重复评价！");
        }
        gradeService.insert(grade);
        //评价完后，要把rater中的已经添加的条目删除
        String commentIds = Arrays.stream(rater.getCommentIds().split(",")).filter(i->!i.equals(String.valueOf(grade.getMatchItemId()))).collect(Collectors.joining(","));
        String doneCommentIds = rater.getDoneCommentIds();
        System.out.println(doneCommentIds);
        if(StringUtils.nullOrEmpty(doneCommentIds)){
            System.out.println("真的是null");
            doneCommentIds =  String.valueOf(grade.getMatchItemId());
        }else{
            doneCommentIds = doneCommentIds  + "," + grade.getMatchItemId();
        }
        rater.setCommentIds(commentIds);
        rater.setDoneCommentIds(doneCommentIds);
        tmpRaterService.updateByPrimaryKey(rater);
        return setJsonResult("success","true");
    }

    public void checkAuth(int matchItemId,RaterLevel raterLevel){
        //判断当前用户有没有资格给该matchItemId评分
        TmpRater rater = hostHolder.getRater();
        if (rater == null || rater.getLevel() != raterLevel.getLevel())
            throw new BaseException("未授权");

        //判断是否过期
        if (tmpRaterService.checkEnd(rater) || tmpRaterService.checkStart(rater))
            throw new BaseException("当前账户已过期或未到评分时间");

        //判断MatchItem是否处于结题检查阶段
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.CONCLUSION_CHECK.getId())
            throw new BaseException("当前比赛已完成，不允许进行评价");

        //判断当前用户是否有评论该matchItem的资格,重新从数据库中获取，防止有修改
        rater = tmpRaterService.selectByUsername(rater.getUsername());
        if (rater.getCommentIds() == null) throw new BaseException("禁止访问");
        if (!Arrays.stream(rater.getCommentIds().split(",")).anyMatch(i -> i.equals(String.valueOf(matchItemId)))) {
            throw new BaseException("没有权限评价此比赛");
        }
    }

    @GetMapping("/admin/grade/{id}")
    public String gradeMatchItem(@PathVariable("id") int matchItemId, Model model) {
        //判断是否有权限访问
        checkAuth(matchItemId,RaterLevel.SCHOOL);

        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        model.addAttribute("item",matchItem);
        return SUPER_GRADE;
    }

    @PostMapping("/admin/grade/submit")
    @ResponseBody
    public String gradeToMatchItemOfSuper(Grade grade){
        checkAuth(grade.getMatchItemId(),RaterLevel.SCHOOL);
        if(StringUtils.nullOrEmpty(grade.getComment(),String.valueOf(grade.getScore())))
            return setJsonResult("error","输入不得为空");
        TmpRater rater = hostHolder.getRater();
        grade.setLevel(rater.getLevel());
        grade.setRaterId(rater.getId());

        //查看之前是否已经添加过一次
        List<Grade> grades = gradeService.selectByRaterIdAndMatchItemId(grade.getMatchItemId(),rater.getId());
        if(grades == null || grades.size() >= 1){
            //之前已经添加过，不需要添加
            return setJsonResult("error","您已经评价过该比赛，不允许重复评价！");
        }
        gradeService.insert(grade);
        //评价完后，要把rater中的已经添加的条目删除
        String commentIds = Arrays.stream(rater.getCommentIds().split(",")).filter(i->!i.equals(String.valueOf(grade.getMatchItemId()))).collect(Collectors.joining(","));
        String doneCommentIds = rater.getDoneCommentIds();
        System.out.println(doneCommentIds);
        if(StringUtils.nullOrEmpty(doneCommentIds)){
            System.out.println("真的是null");
            doneCommentIds =  String.valueOf(grade.getMatchItemId());
        }else{
            doneCommentIds = doneCommentIds  + "," + grade.getMatchItemId();
        }
        rater.setCommentIds(commentIds);
        rater.setDoneCommentIds(doneCommentIds);
        tmpRaterService.updateByPrimaryKey(rater);
        return setJsonResult("success","true");
    }

}

