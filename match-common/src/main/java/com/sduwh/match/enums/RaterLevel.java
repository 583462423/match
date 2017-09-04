package com.sduwh.match.enums;

/**
 * Created by qxg on 17-7-12.
 * 评委等级表
 */
public enum RaterLevel {

    ACADEMY(1,"院级"),
    SCHOOL(2,"校级");

    private Integer level;
    private String des;

    RaterLevel(Integer level,String des){
        this.level = level;
        this.des = des;
    }

    public Integer getLevel(){
        return level;
    }
}
