package com.sduwh.match.service.matchitem;

import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.wrapper.MatchItemDetail;
import com.sduwh.match.model.wrapper.MatchItemWithScore;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.service.BaseService;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
public interface MatchItemService extends BaseService<MatchItem,Integer> {
    int CHECK_PASS_TEACHER = 1;
    int CHECK_PASS_ACADEMY = 2;
    int CHECK_PASS_CAMPUS = 3;
    List<MatchItem> selectAllByStringIds(String ids);
    List<MatchItem> selectAllByInfoId(Integer id);
    List<MatchItem> selectByStageId(int stageId);
    /** 指导老师审核阶段，如果都审核完毕，就进行更新当前阶段*/
    int updateWhenTeacherAllChecked(int id);

    /** 查找到对应的待审核的记录,并且将结果保存在model中,如果String不为空，说明需要重定向*/
    String checkDetail(Model model,int id);

    /** 将对应的比赛审核通过,如果返回的结果不为空，则表示重定向,flag表示指导老师审核通过还是学院或者学校*/
    String checkPass(int id,int flag);

    /** 审核不通过*/
    String checkNoPass(int id,int flag);
    
    /** 正常的将比赛设置为下一个阶段*/
    int updateAndSetNextStage(MatchItem matchItem,Stage stage);

    List<MatchItem> selectByNowStageId(int nowStageId);

    boolean checkIsRunning(MatchItem matchItem);

    /** 查询当前评奖阶段的所有比赛*/
    List<MatchItemWithScore> selectAwardsWithScore(int matchInfoId);

    /** 通过matchItemId来查找比赛的详细信息*/
    MatchItemDetail selectDetailById(int matchItemId);
}
