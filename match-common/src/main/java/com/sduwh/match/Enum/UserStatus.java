package com.sduwh.match.Enum;

/**
 * Created by qxg on 17-6-29.
 */
public enum UserStatus {

    NORMAL(1,"正常"),         //正常状态
    FREEZE(2,"冻结"),         //冻结状态
    NOT_ACTIVE(3,"未激活");   //未激活状态

    private Integer id;
    private String des;

    private UserStatus(Integer id,String des) {
        this.id = id;
        this.des = des;
    }

    public Integer getId() {
        return id;
    }

    public String getDes() {
        return des;
    }
}
