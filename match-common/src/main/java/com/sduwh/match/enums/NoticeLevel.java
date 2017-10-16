package com.sduwh.match.enums;

/** 通告的级别，发送给学生，还是全体成员*/
public enum NoticeLevel {

    ALL(0,"全体成员"),
    STUDENT(1,"所有学生"),
    TEACHER(2,"所有教师"),
    ADMIN(3,"所有管理员");

    private int code;
    private String des;
    NoticeLevel(int code ,String des){
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
