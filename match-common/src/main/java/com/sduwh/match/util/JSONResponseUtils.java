package com.sduwh.match.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxg on 17-9-4.
 */
public class JSONResponseUtils {
    public static String setJSONResponse(String ... info){
        if((info.length &1) == 1)throw new RuntimeException("Map结果参数不得为基数个");
        Map<String,String> res = new HashMap<>();
        for(int i=0; i<info.length; i+=2){
            res.put(info[i],info[i+1]);
        }
        return JSONObject.toJSONString(res);
    }

    public Map<String,String> setResult(String ... info){
        if((info.length &1) == 1)throw new RuntimeException("Map结果参数不得为基数个");
        Map<String,String> res = new HashMap<>();
        for(int i=0; i<info.length; i+=2){
            res.put(info[i],info[i+1]);
        }
        return res;
    }
}
