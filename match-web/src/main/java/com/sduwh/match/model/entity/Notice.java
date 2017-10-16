package com.sduwh.match.model.entity;

import java.util.Date;

/** 通告*/
public class Notice {
    private int id;
    private String title;    //标题
    private String content;  //内容
    private int level;       //级别，该通告是给学生的，还是给老师的，还是给谁谁谁的
    private Date startTime;  //开始时间
    private Date endTime;    //结束时间
    private int isValid;     //是否有效，过期的都是无效的，没过期的都有效，为了查询方便

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", level=" + level +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isValid=" + isValid +
                '}';
    }
}
