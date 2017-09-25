package com.sduwh.match.model.entity;

import java.sql.Timestamp;
import java.util.Date;

public class ResearchLog {
    private Integer id;

    private String title;

    private Integer isPublic;

    private Integer times;

    private Date lastTime;

    private Integer matchItemId;

    private String content;



    private String comment;

    /** 这里注意startTime不是日期类型，而是int，表示开始的小时数*/
    private int startTime;
    private int endTime;

    /** 表示日志开始的时间*/
    private Timestamp time;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(Integer matchItemId) {
        this.matchItemId = matchItemId;
    }

    @Override
    public String toString() {
        return "ResearchLog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isPublic=" + isPublic +
                ", times=" + times +
                ", lastTime=" + lastTime +
                ", matchItemId=" + matchItemId +
                ", content='" + content + '\'' +
                ", comment='" + comment + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", time=" + time +
                '}';
    }
}