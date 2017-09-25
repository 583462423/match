package com.sduwh.match.model.entity;

import java.util.Date;

/**
 * Created by qxg on 17-9-14.
 */
public class TransferMember {
    private int id;
    private Date createTime;
    private String fromMembers;
    private String toMembers;
    private int matchItemId;
    private int stage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFromMembers() {
        return fromMembers;
    }

    public void setFromMembers(String fromMembers) {
        this.fromMembers = fromMembers;
    }

    public String getToMembers() {
        return toMembers;
    }

    public void setToMembers(String toMembers) {
        this.toMembers = toMembers;
    }

    public int getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(int matchItemId) {
        this.matchItemId = matchItemId;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "TransferMember{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", fromMembers='" + fromMembers + '\'' +
                ", toMembers='" + toMembers + '\'' +
                ", matchItemId=" + matchItemId +
                ", stage=" + stage +
                '}';
    }
}
