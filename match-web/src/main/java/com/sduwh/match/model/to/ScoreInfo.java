package com.sduwh.match.model.to;

import java.util.List;

/**
 * Created by qxg on 17-9-20.
 */
public class ScoreInfo {
    /** 比赛id*/
    private int matchInfoId;
    /** 所有比赛的TO类型*/
    private List<MatchItemTO> matchItemTOS;
    /** 获取所有的处于结题检查阶段的比赛，并且处于某评分段前边的比赛的个数*/
    private long cnt;
    /** 当前符合条件比赛的个数，其实就是TOS.size()*/
    private int nowCnt;
    /** 剩余的还未审核的比赛个数*/
    private long last;
    /** 没有进行评分的比赛个数*/
    private int notDoneCnt;

    public int getMatchInfoId() {
        return matchInfoId;
    }

    public void setMatchInfoId(int matchInfoId) {
        this.matchInfoId = matchInfoId;
    }

    public List<MatchItemTO> getMatchItemTOS() {
        return matchItemTOS;
    }

    public void setMatchItemTOS(List<MatchItemTO> matchItemTOS) {
        this.matchItemTOS = matchItemTOS;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public int getNowCnt() {
        return nowCnt;
    }

    public void setNowCnt(int nowCnt) {
        this.nowCnt = nowCnt;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public int getNotDoneCnt() {
        return notDoneCnt;
    }

    public void setNotDoneCnt(int notDoneCnt) {
        this.notDoneCnt = notDoneCnt;
    }
}
