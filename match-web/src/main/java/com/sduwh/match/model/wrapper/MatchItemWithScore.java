package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.MatchItem;

/**
 * Created by qxg on 17-9-22.
 */
public class MatchItemWithScore {
    private MatchItem matchItem;
    private float score;

    public MatchItem getMatchItem() {
        return matchItem;
    }

    public void setMatchItem(MatchItem matchItem) {
        this.matchItem = matchItem;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "MatchItemWithScore{" +
                "matchItem=" + matchItem +
                ", score=" + score +
                '}';
    }
}
