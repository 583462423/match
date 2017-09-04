package com.sduwh.match.exception;

import com.sduwh.match.exception.base.BaseException;

/**
 * Created by qxg on 17-8-15.
 * 申请比赛抛出的异常类
 */
public class IllegalApplyMatchException extends BaseException{

    public IllegalApplyMatchException(String msg){
        super(msg);
    }
}
