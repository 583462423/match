package com.sduwh.match.model.entity;

import java.util.Date;

public class Stage {
    private Integer id;

    private String name;

    private Integer type;

    private Date startTime;

    private Date endTime;

    private Integer stageFlag;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStageFlag() {
        return stageFlag;
    }

    public void setStageFlag(Integer stageFlag) {
        this.stageFlag = stageFlag;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stageFlag=" + stageFlag +
                '}';
    }
}