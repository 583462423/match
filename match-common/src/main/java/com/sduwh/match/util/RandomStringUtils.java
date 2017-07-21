package com.sduwh.match.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * Created by qxg on 17-7-19.
 * 生成自动random字符串的工具累
 */
public class RandomStringUtils {

    /** 默认生成10位帐号*/
    public static String getRandomString(){
        return getRandomString(10);
    }

    public static String getRandomString(String salt){
        return getRandomString(10,salt);
    }

    public static String getRandomString(int length){
        return getRandomString(length,"");
    }

    public static String getRandomString(int length,String salt){
        return DigestUtils
                .md5Hex(String.valueOf(
                        UUID.randomUUID().toString().substring(0,8)
                ) + salt).substring(0,length);
    }

    public static String getRandomStringUppercase(){
        return getRandomString(10).toUpperCase();
    }

    public static String getRandomStringUppercase(String salt){
        return getRandomString(10,salt).toUpperCase();
    }

    public static String getRandomStringUppercase(int length){
        return getRandomString(length).toUpperCase();
    }

    public static String getRandomStringUppercase(int length,String salt){
        return getRandomString(length,salt).toUpperCase();
    }

}
