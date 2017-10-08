package com.sduwh.match.enums;

/** 比赛是否按照时间顺序执行*/
public enum MatchByTimeEnum {

    YES(1,"是啊是啊"),NO(-1,"不按照");

    MatchByTimeEnum(int code,String des){
        this.code = code;
        this.des = des;
    }

    private int code;
    private String des;

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
