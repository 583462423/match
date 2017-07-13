package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.TmpRater;

import java.util.List;

/**
 * Created by qxg on 17-7-12.
 * 临时评委的包装类
 */
public class RaterWrapper {
    private TmpRater tmpRater;
    private List<MatchItem> noDone; //还未评论过的比赛
    private List<MatchItem> done;   //已经评论过的比赛

    public TmpRater getTmpRater() {
        return tmpRater;
    }

    public void setTmpRater(TmpRater tmpRater) {
        this.tmpRater = tmpRater;
    }

    public List<MatchItem> getNoDone() {
        return noDone;
    }

    public void setNoDone(List<MatchItem> noDone) {
        this.noDone = noDone;
    }

    public List<MatchItem> getDone() {
        return done;
    }

    public void setDone(List<MatchItem> done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "RaterWrapper{" +
                "tmpRater=" + tmpRater +
                ", noDone=" + noDone +
                ", done=" + done +
                '}';
    }
}
