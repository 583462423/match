package com.sduwh.match.model.entity;

import com.sduwh.match.Enum.RaterLevel;
import org.omg.CORBA.INTERNAL;

import java.util.Date;

public class TmpRater {
    private Integer id;

    private String username;

    private String password;

    private String commentIds;

    private String doneCommentIds;

    private Integer level;

    private Date startTime;

    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(String commentIds) {
        this.commentIds = commentIds == null ? null : commentIds.trim();
    }

    public String getDoneCommentIds() {
        return doneCommentIds;
    }

    public void setDoneCommentIds(String doneCommentIds) {
        this.doneCommentIds = doneCommentIds == null ? null : doneCommentIds.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
}