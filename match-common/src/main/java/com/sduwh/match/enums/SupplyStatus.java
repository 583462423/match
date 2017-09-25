package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-11.
 * 补充申报的状态
 */
public enum SupplyStatus {

    IS_SUPPLY(1,"补充申请"),
    IS_NOT_SUPPLY(-1,"正常申请状态"),
    OPEN_SUPPLY(2,"补录开启"),
    CLOSE_SUPPLY(-2,"补录关闭");

    private int code;
    private String des;

    SupplyStatus(int code, String des){
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
