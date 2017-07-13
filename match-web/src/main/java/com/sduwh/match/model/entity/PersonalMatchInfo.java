package com.sduwh.match.model.entity;

public class PersonalMatchInfo {
    private Integer id;

    private Integer userId;

    private Integer matchItemId;

    private Integer type;

    private Integer status;

    private String jobAssignment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(Integer matchItemId) {
        this.matchItemId = matchItemId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJobAssignment() {
        return jobAssignment;
    }

    public void setJobAssignment(String jobAssignment) {
        this.jobAssignment = jobAssignment == null ? null : jobAssignment.trim();
    }
}