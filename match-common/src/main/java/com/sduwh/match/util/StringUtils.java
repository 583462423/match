package com.sduwh.match.util;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by qxg on 17-7-13.
 */
public class StringUtils {

    /** 判断字符串是否为空*/
    public static boolean nullOrEmpty(String s){
        return (null==s || "".equals(s));
    }

    /** 判断所有传入的字符串是否为空*/
    public static boolean nullOrEmpty(String...s){
        return Arrays.stream(s).anyMatch(StringUtils::nullOrEmpty);
    }


    /** 判断字符串是否为空，如果是空，则进行下一步操作*/
    public static void nullEmptyThen(String s, Consumer<String> c){
        if (nullOrEmpty(s)) c.accept(s);
    }

    /** 判断字符串是否为空，如果非空，则进行下一步操作*/
    public static void notNullEmptyThen(String s,Consumer<String> c){
        if (!nullOrEmpty(s)) c.accept(s);
    }
}
