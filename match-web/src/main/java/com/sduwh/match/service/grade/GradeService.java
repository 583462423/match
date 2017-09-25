package com.sduwh.match.service.grade;

import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.model.to.ScoreInfo;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface GradeService extends BaseService<Grade,Integer> {
    List<Grade> selectByRaterIdAndMatchItemId(int matchItemId, int raterId);
    /** 获取某个比赛阶段的对应的ScoreInfo信息*/
    ScoreInfo getScoreInfo(RaterLevel raterLevel, String jedisKey, int matchInfoId, ConcludingStatementStage concludingStatementStage);

    /** 通过matchItem获取所有的Grade*/
    List<Grade> selectByMatchItemId(int matchItemId);

    /** 通过某个比赛id来获取平均分*/
    float selectAverangeByMatchItem(int matchItemId);
}
