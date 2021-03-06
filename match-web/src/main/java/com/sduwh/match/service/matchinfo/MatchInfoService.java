package com.sduwh.match.service.matchinfo;

import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.to.MatchInfoTO;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface MatchInfoService extends BaseService<MatchInfo,Integer> {
    List<MatchInfo> selectAll();
    public int createMatchInfoByTO(MatchInfoTO to);
    public int updateMatchInfoByTO(MatchInfoTO to);

    /** 当前比赛是否已经结束*/
    boolean checkEnd(MatchInfo info);

    /** 当前比赛是否在运行*/
    boolean checkIsRunning(MatchInfo matchInfo);

    /** 删除该类型的比赛，以及联动的所有内容*/
    boolean deleteAllOtherByMatchInfoId(int id);

    /** 检查比赛名是否被占用*/
    List<MatchInfo> selectByName(String name);
}
