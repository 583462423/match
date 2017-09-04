package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-1.
 * 记录当前比赛的状态
 */
public enum MatchStatus {

    ALL_DONE(-1,"比赛已完成"),
    TEACHER_NOT_PASS(-2,"导师审核未通过"),
    ACADEMY_NOT_PASS(-3,"学院审核未通过"),
    SUPER_NOT_PASS(-4,"学校审核未通过");

    int value;
    String name;

    MatchStatus(int value,String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
