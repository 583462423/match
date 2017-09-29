package com.sduwh.match.model.entity;

import java.sql.Timestamp;

public class MatchInfo {
    private Integer id;

    private String name;      //比赛名称

    private Integer type1;    //比赛一级分类

    private Integer type2;    //比赛二级分类

    private Integer level;    //比赛级别,校际还是院级

    private String allStage;  //所有阶段

    private Integer leaderNum; //队长数目

    private Integer memberNum; //成员数目

    private Integer teacherNum;//指导老师数目

    private Integer leaderInNum; //队长能够参与比赛的数目

    private Integer memberInNum; //成员可以参与的比赛的个数

    private Integer teacherInNum; //指导老师可以指导的数目

    private Timestamp createTime; //创建时间

    private Timestamp startTime;  //开始时间

    private Timestamp endTime;    //结束时间

    /** 表示当前是否开放补充申报状态*/
    private int supply;

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
        this.name = name == null ? null : name.trim();
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

    public String getAllStage() {
        return allStage;
    }

    public void setAllStage(String allStage) {
        this.allStage = allStage == null ? null : allStage.trim();
    }

    public Integer getLeaderNum() {
        return leaderNum;
    }

    public void setLeaderNum(Integer leaderNum) {
        this.leaderNum = leaderNum;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public Integer getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
    }

    public Integer getLeaderInNum() {
        return leaderInNum;
    }

    public void setLeaderInNum(Integer leaderInNum) {
        this.leaderInNum = leaderInNum;
    }

    public Integer getMemberInNum() {
        return memberInNum;
    }

    public void setMemberInNum(Integer memberInNum) {
        this.memberInNum = memberInNum;
    }

    public Integer getTeacherInNum() {
        return teacherInNum;
    }

    public void setTeacherInNum(Integer teacherInNum) {
        this.teacherInNum = teacherInNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", level=" + level +
                ", allStage='" + allStage + '\'' +
                ", leaderNum=" + leaderNum +
                ", memberNum=" + memberNum +
                ", teacherNum=" + teacherNum +
                ", leaderInNum=" + leaderInNum +
                ", memberInNum=" + memberInNum +
                ", teacherInNum=" + teacherInNum +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", supply=" + supply +
                '}';
    }
}