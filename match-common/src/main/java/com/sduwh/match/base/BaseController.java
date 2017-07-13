package com.sduwh.match.base;

import com.sduwh.match.common.ResponseResult;

/**
 * Created by qxg on 17-6-28.
 */
public abstract class BaseController {

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

}
