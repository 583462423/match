package com.sduwh.match.common;

/**
 * Created by qxg on 17-6-28.
 * 封装json返回结果
 */
public class ResponseResult {
    /** 返回结果 */
    private boolean success;

    /** 返回信息 */
    private String message;

    /** 返回数据 */
    private Object data;

    public ResponseResult(boolean success) {
        super();
        this.success = success;
    }

    public ResponseResult(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public ResponseResult(boolean success, Object data) {
        super();
        this.success = success;
        this.data = data;
    }

    public ResponseResult(boolean success, String message, Object data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
