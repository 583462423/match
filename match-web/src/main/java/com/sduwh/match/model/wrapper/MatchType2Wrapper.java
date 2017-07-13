package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;

/**
 * Created by qxg on 17-7-5.
 */
public class MatchType2Wrapper {
    MatchType matchType;
    MatchType2 matchType2;

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

    @Override
    public String toString() {
        return "MatchType2Wrapper{" +
                "matchType=" + matchType +
                ", matchType2=" + matchType2 +
                '}';
    }
}
