package com.sduwh.match.model.to;

import com.sduwh.match.model.entity.MatchItem;

/**
 * Created by qxg on 17-9-18.
 */
public class MatchItemTO {
    private MatchItem matchItem;
    private int raterCnt;       //已经生成的对matchItem的评委的个数
    private int doneRater;      //已经评价的评委个数
    private float average;      //已经评价过的人的平均分数

    public int getDoneRater() {
        return doneRater;
    }

    public void setDoneRater(int doneRater) {
        this.doneRater = doneRater;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public MatchItem getMatchItem() {
        return matchItem;
    }

    public void setMatchItem(MatchItem matchItem) {
        this.matchItem = matchItem;
    }

    public int getRaterCnt() {
        return raterCnt;
    }

    public void setRaterCnt(int raterCnt) {
        this.raterCnt = raterCnt;
    }

    @Override
    public String toString() {
        return "MatchItemTO{" +
                "matchItem=" + matchItem +
                ", raterCnt=" + raterCnt +
                ", doneRater=" + doneRater +
                ", average=" + average +
                '}';
    }
}
