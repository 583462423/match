package com.sduwh.match.model.entity;

/**
 * Created by qxg on 17-9-1.
 * 审核表
 */
public class Pass {
    private int id;
    private int userId;
    private int matchItemId;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(int matchItemId) {
        this.matchItemId = matchItemId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "id=" + id +
                ", userId=" + userId +
                ", matchItemId=" + matchItemId +
                ", status=" + status +
                '}';
    }
}
