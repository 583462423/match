package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;

import java.util.List;

/**
 * Created by qxg on 17-7-5.
 */
public class MatchTypeWrapper {
    private MatchType matchType;
    private List<MatchType2> matchType2s;

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public List<MatchType2> getMatchType2s() {
        return matchType2s;
    }

    public void setMatchType2s(List<MatchType2> matchType2s) {
        this.matchType2s = matchType2s;
    }

    @Override
    public String toString() {
        return "MatchTypeWrapper{" +
                "matchType=" + matchType +
                ", matchType2s=" + matchType2s +
                '}';
    }
}
