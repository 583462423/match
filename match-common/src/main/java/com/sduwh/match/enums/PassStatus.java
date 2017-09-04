package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-1.
 * 审核表的状态信息
 */
public enum PassStatus {


    PASS(1,"已通过"),NO_PASS(-1,"未通过");

    PassStatus(int value,String des){
        this.value = value;
        this.des = des;
    }

    int value;
    String des;

    public int getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}
