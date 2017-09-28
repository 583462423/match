package com.sduwh.match.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.exception.IllegalApplyMatchException;
import com.sduwh.match.exception.base.BaseException;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxg on 17-6-28.
 */
public abstract class BaseController {

    /** 未授权网站*/
    public static final String UNAUTH = "/common/unauth";
    /** 激活网站*/
    public static final String TO_ACTIVE = "";  //待传参数

    /**
     * 成功,返回状态
     * @param success 状态true/false
     * @return
     */
    public ResponseResult success(Boolean success) {
        return new ResponseResult(success);
    }

    /**
     * 成功,返回状态
     * @param success 状态true/false
     * @return
     */
    public ResponseResult success(Boolean success, String message) {
        return new ResponseResult(success, message);
    }

    /**
     * 成功,返回状态
     * @param success 状态true/false
     * @return
     */
    public ResponseResult success(Boolean success, String message, Object data) {
        return new ResponseResult(success, message, data);
    }

    /**
     * 返回json数据
     * @param success 状态true/false
     * @param data 实体
     * @return
     */
    public ResponseResult json(Boolean success, Object data) {
        return new ResponseResult(success, data);
    }

    /**
     * 失败,返回状态及原因
     * @param success 状态true/false
     * @param message 消息
     * @return
     */
    public ResponseResult fail(Boolean success, String message) {
        return new ResponseResult(success, message);
    }

    /**
     * 统一处理异常方法
     * 因为会自动映射，所以如果跑出的不是BaseException，那么就会出现问题
     */
    @ExceptionHandler
    public String exp(HttpServletRequest request,BaseException ex){
        request.setAttribute("ex",ex);
        return expPage;
    }

    private static final String expPage = "/error";

    public Map<String,String> setResult(String ... info){
        if((info.length &1) == 1)throw new BaseException("Map结果参数不得为基数个");
        Map<String,String> res = new HashMap<>();
        for(int i=0; i<info.length; i+=2){
            res.put(info[i],info[i+1]);
        }
        return res;
    }

    public String setJsonResult(String ... info){
        return JSONObject.toJSONString(setResult(info));
    }
}
