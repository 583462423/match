package com.sduwh.match.model.to;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by qxg on 17-6-30.
 * 为了业务方便，适配业务的类型
 */
public class MatchInfoTO {
    private Integer id;
    private String name;
    private Integer type1;
    private Integer type2;
    private Integer level;
    private Integer leaderNum;
    private Integer leaderInNum;
    private Integer memberNum;
    private Integer memberInNum;
    private Integer teacherNum;
    private Integer teacherInNum;
    private List<Integer> isChoose;
    private List<Timestamp> startTime;
    private List<Timestamp> endTime;
    private List<String> stageName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType1() {
        return type1;
    }

    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    public Integer getType2() {
        return type2;
    }

    public void setType2(Integer type2) {
        this.type2 = type2;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLeaderNum() {
        return leaderNum;
    }

    public void setLeaderNum(Integer leaderNum) {
        this.leaderNum = leaderNum;
    }

    public Integer getLeaderInNum() {
        return leaderInNum;
    }

    public void setLeaderInNum(Integer leaderInNum) {
        this.leaderInNum = leaderInNum;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public Integer getMemberInNum() {
        return memberInNum;
    }

    public void setMemberInNum(Integer memberInNum) {
        this.memberInNum = memberInNum;
    }

    public Integer getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
    }

    public Integer getTeacherInNum() {
        return teacherInNum;
    }

    public void setTeacherInNum(Integer teacherInNum) {
        this.teacherInNum = teacherInNum;
    }

    public List<Integer> getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(List<Integer> isChoose) {
        this.isChoose = isChoose;
    }


    public List<Timestamp> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<Timestamp> startTime) {
        this.startTime = startTime;
    }

    public List<Timestamp> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<Timestamp> endTime) {
        this.endTime = endTime;
    }

    public List<String> getStageName() {
        return stageName;
    }

    public void setStageName(List<String> stageName) {
        this.stageName = stageName;
    }

    @Override
    public String toString() {
        return "MatchInfoTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", level=" + level +
                ", leaderNum=" + leaderNum +
                ", leaderInNum=" + leaderInNum +
                ", memberNum=" + memberNum +
                ", memberInNum=" + memberInNum +
                ", teacherNum=" + teacherNum +
                ", teacherInNum=" + teacherInNum +
                ", isChoose=" + isChoose +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stageName=" + stageName +
                '}';
    }
}

