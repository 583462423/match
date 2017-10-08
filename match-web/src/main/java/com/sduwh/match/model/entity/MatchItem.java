package com.sduwh.match.model.entity;

public class MatchItem {
    private Integer id;

    private String leaderIds;

    private String title;    //比赛主题

    private String memberIds;

    private Integer applyId;

    private String teacherIds;


    private Integer nowStageId;

    private Integer nextStageId;

    private Integer academyId;

    private Integer matchInfoId;

    /** 标志比赛执行是否按照时间来*/
    private Integer byTime;

    /** 表示是否是补充申报的作品，因为补充申报的作品不能受时间影响，如果时间过期也不影响，但是不能提前*/
    private Integer isSupply;

    public int getByTime() {
        return byTime;
    }

    public void setByTime(int byTime) {
        this.byTime = byTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeaderIds() {
        return leaderIds;
    }

    public void setLeaderIds(String leaderIds) {
        this.leaderIds = leaderIds == null ? null : leaderIds.trim();
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds == null ? null : memberIds.trim();
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(String teacherIds) {
        this.teacherIds = teacherIds == null ? null : teacherIds.trim();
    }

    public Integer getNowStageId() {
        return nowStageId;
    }

    public void setNowStageId(Integer nowStageId) {
        this.nowStageId = nowStageId;
    }

    public Integer getNextStageId() {
        return nextStageId;
    }

    public void setNextStageId(Integer nextStageId) {
        this.nextStageId = nextStageId;
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }

    public Integer getMatchInfoId() {
        return matchInfoId;
    }

    public void setMatchInfoId(Integer matchInfoId) {
        this.matchInfoId = matchInfoId;
    }

    public Integer getIsSupply() {
        return isSupply;
    }

    public void setIsSupply(Integer isSupply) {
        this.isSupply = isSupply;
    }

    @Override
    public String toString() {
        return "MatchItem{" +
                "id=" + id +
                ", leaderIds='" + leaderIds + '\'' +
                ", title='" + title + '\'' +
                ", memberIds='" + memberIds + '\'' +
                ", applyId=" + applyId +
                ", teacherIds='" + teacherIds + '\'' +
                ", nowStageId=" + nowStageId +
                ", nextStageId=" + nextStageId +
                ", academyId=" + academyId +
                ", matchInfoId=" + matchInfoId +
                ", isSupply=" + isSupply +
                '}';
    }
}