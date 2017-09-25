package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-19.
 */
public enum LoginType {

    /** 用户登陆的时候，根据登陆类型，进行不同的数据库调用*/
    USER("user"),RATER("rater");

    private String type;
    LoginType(String type){
        this.type = type;
    }
}
