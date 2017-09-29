package com.sduwh.match.util;


/** 上传申请表等项目统一管理项目名*/
public class UploadFileNameUtils {

    static String suffix = "";
    /** 每个用户的每个比赛只能有一个申请表，所以可以根据这两个字段来获取到对应的申请表名*/
    public static String getApplyFileName(String username,int matchItemId,String suffix){
        return MD5Utils.md5(username + matchItemId) + "." + suffix;
    }
}
