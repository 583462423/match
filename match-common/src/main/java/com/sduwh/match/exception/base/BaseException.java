package com.sduwh.match.exception.base;

/**
 * Created by qxg on 17-8-15.
 */
public class BaseException extends RuntimeException {
    private String msg;

    public BaseException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
