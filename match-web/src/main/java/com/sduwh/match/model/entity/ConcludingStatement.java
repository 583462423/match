package com.sduwh.match.model.entity;

/**
 * Created by qxg on 17-9-16.
 */
public class ConcludingStatement {
    private int id;
    private String completionSituation;   //完成情况
    private String researchResult;        //研究结果
    private String selfJudge;             //个人评价
    private String priceSituation;        //经费情况
    private String viewOfTeacher;         //教师评价
    private String viewOfAcademy;         //学院评价
    private String viewOfSuper;           //学校评价
    private int matchItemId;
    private String upPos;                 //附件上传位置
    private int stage;                    //当前阶段

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompletionSituation() {
        return completionSituation;
    }

    public void setCompletionSituation(String completionSituation) {
        this.completionSituation = completionSituation;
    }

    public String getResearchResult() {
        return researchResult;
    }

    public void setResearchResult(String researchResult) {
        this.researchResult = researchResult;
    }

    public String getSelfJudge() {
        return selfJudge;
    }

    public void setSelfJudge(String selfJudge) {
        this.selfJudge = selfJudge;
    }

    public String getPriceSituation() {
        return priceSituation;
    }

    public void setPriceSituation(String priceSituation) {
        this.priceSituation = priceSituation;
    }

    public String getViewOfTeacher() {
        return viewOfTeacher;
    }

    public void setViewOfTeacher(String viewOfTeacher) {
        this.viewOfTeacher = viewOfTeacher;
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

    public String getUpPos() {
        return upPos;
    }

    public void setUpPos(String upPos) {
        this.upPos = upPos;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "ConcludingStatement{" +
                "id=" + id +
                ", completionSituation='" + completionSituation + '\'' +
                ", researchResult='" + researchResult + '\'' +
                ", selfJudge='" + selfJudge + '\'' +
                ", priceSituation='" + priceSituation + '\'' +
                ", viewOfTeacher='" + viewOfTeacher + '\'' +
                ", viewOfAcademy='" + viewOfAcademy + '\'' +
                ", viewOfSuper='" + viewOfSuper + '\'' +
                ", matchItemId=" + matchItemId +
                ", upPos='" + upPos + '\'' +
                ", stage=" + stage +
                '}';
    }
}
