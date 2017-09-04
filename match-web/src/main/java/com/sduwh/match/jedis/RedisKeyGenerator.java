package com.sduwh.match.jedis;

/**
 * Created by qxg on 17-8-28.
 */
public class RedisKeyGenerator {
    private static final String PROJECT_NAME = "MATCH";
    private static final String SPLIT = "_";
    private static final String TEACHER_IN_PROJECT_IDS = "TEACHER_IN_PROJECT_IDS";


    /** 返回老师参与的项目的key值*/
    public static String getTeacherProjects(){
        return PROJECT_NAME + SPLIT + TEACHER_IN_PROJECT_IDS;
    }
}
