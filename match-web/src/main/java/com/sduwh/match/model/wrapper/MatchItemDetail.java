package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.*;

import java.util.List;

public class MatchItemDetail {
    MatchItem matchItem;
    User leader;
    List<User> members;
    Apply apply;
    List<User> teachers;
    Academy academy;     //所属学院
    MatchInfo matchInfo;  //所属比赛

    public MatchItem getMatchItem() {
        return matchItem;
    }

    public void setMatchItem(MatchItem matchItem) {
        this.matchItem = matchItem;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teaches) {
        this.teachers = teaches;
    }

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfo matchInfo) {
        this.matchInfo = matchInfo;
    }
}
