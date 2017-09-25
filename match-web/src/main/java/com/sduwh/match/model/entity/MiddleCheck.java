package com.sduwh.match.model.entity;

/**
 * Created by qxg on 17-9-11.
 */
public class MiddleCheck {
    private int id;
    private String comuWithTeacher;
    private String projectResult;
    private String isExpect;
    private String costSituation;
    private String pointByStudent;
    private String pointByTeacher;
    private String followPlan;
    private String followPoint;
    private String viewOfTeacher;
    private String levelByTeacher;
    private String viewOfAcademy;
    private String viewOfSuper;
    private int matchItemId;

    /** 标记当前的状态，如学生自我评价阶段等。*/
    private int stage;



    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComuWithTeacher() {
        return comuWithTeacher;
    }

    public void setComuWithTeacher(String comuWithTeacher) {
        this.comuWithTeacher = comuWithTeacher;
    }

    public String getProjectResult() {
        return projectResult;
    }

    public void setProjectResult(String projectResult) {
        this.projectResult = projectResult;
    }

    public String getIsExpect() {
        return isExpect;
    }

    public void setIsExpect(String isExpect) {
        this.isExpect = isExpect;
    }

    public String getCostSituation() {
        return costSituation;
    }

    public void setCostSituation(String costSituation) {
        this.costSituation = costSituation;
    }

    public String getPointByStudent() {
        return pointByStudent;
    }

    public void setPointByStudent(String pointByStudent) {
        this.pointByStudent = pointByStudent;
    }

    public String getPointByTeacher() {
        return pointByTeacher;
    }

    public void setPointByTeacher(String pointByTeacher) {
        this.pointByTeacher = pointByTeacher;
    }

    public String getFollowPlan() {
        return followPlan;
    }

    public void setFollowPlan(String followPlan) {
        this.followPlan = followPlan;
    }

    public String getFollowPoint() {
        return followPoint;
    }

    public void setFollowPoint(String followPoint) {
        this.followPoint = followPoint;
    }

    public String getViewOfTeacher() {
        return viewOfTeacher;
    }

    public void setViewOfTeacher(String viewOfTeacher) {
        this.viewOfTeacher = viewOfTeacher;
    }

    public String getLevelByTeacher() {
        return levelByTeacher;
    }

    public void setLevelByTeacher(String levelByTeacher) {
        this.levelByTeacher = levelByTeacher;
    }

    public String getViewOfAcademy() {
        return viewOfAcademy;
    }

    public void setViewOfAcademy(String viewOfAcademy) {
        this.viewOfAcademy = viewOfAcademy;
    }

    public String getViewOfSuper() {
        return viewOfSuper;
    }

    public void setViewOfSuper(String viewOfSuper) {
        this.viewOfSuper = viewOfSuper;
    }

    public int getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(int matchItemId) {
        this.matchItemId = matchItemId;
    }

    @Override
    public String toString() {
        return "MiddleCheck{" +
                "id=" + id +
                ", comuWithTeacher='" + comuWithTeacher + '\'' +
                ", projectResult='" + projectResult + '\'' +
                ", isExpect='" + isExpect + '\'' +
                ", costSituation='" + costSituation + '\'' +
                ", pointByStudent='" + pointByStudent + '\'' +
                ", pointByTeacher='" + pointByTeacher + '\'' +
                ", followPlan='" + followPlan + '\'' +
                ", followPoint='" + followPoint + '\'' +
                ", viewOfTeacher='" + viewOfTeacher + '\'' +
                ", levelByTeacher='" + levelByTeacher + '\'' +
                ", viewOfAcademy='" + viewOfAcademy + '\'' +
                ", viewOfSuper='" + viewOfSuper + '\'' +
                ", matchItemId=" + matchItemId +
                ", stage=" + stage +
                '}';
    }
}
