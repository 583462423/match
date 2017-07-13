package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.model.entity.Stage;

import java.util.List;

/**
 * MatchInfo的包装类型，不但有MatchInfo的各种信息，还包含其外键指向的类
 */
public class MatchInfoWrapper  {
    private MatchInfo matchInfo;
    private MatchType matchType;              //type1对应的类型
    private MatchType2 matchType2;            //type2对应的类型
    private List<Stage> allStage;             //所有状态

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfo matchInfo) {
        this.matchInfo = matchInfo;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public MatchType2 getMatchType2() {
        return matchType2;
    }

    public void setMatchType2(MatchType2 matchType2) {
        this.matchType2 = matchType2;
    }

    public List<Stage> getAllStage() {
        return allStage;
    }

    public void setAllStage(List<Stage> allStage) {
        this.allStage = allStage;
    }
}
