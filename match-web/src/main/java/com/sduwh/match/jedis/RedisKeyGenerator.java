package com.sduwh.match.jedis;

/**
 * Created by qxg on 17-8-28.
 */
public class RedisKeyGenerator {
    private static final String PROJECT_NAME = "MATCH";
    private static final String SPLIT = "_";
    private static final String TEACHER_IN_PROJECT_IDS = "TEACHER_IN_PROJECT_IDS";
    private static final String QUEUE = "QUEUE";
    private static final String LIST_SHOW_HANDLE_HAS_DONE = "LIST_SHOW_HANDLE_HAS_DONE";   //名单公布已经处理的stage
    private static final String TEACHER_MIDDLE_CHECK = "TEACHER_MIDDLE_CHECK";
    private static final String TEACHER_CONCLUDING_CHECK = "TEACHER_CONCLUDING_CHECK";    //结题报告阶段
    private static final String ACADEMY_SCORE = "ACADEMY_SCORE";
    private static final String SUPER_SCORE = "SUPER_SCORE";
    private static final String SUPER_AWARD = "SUPER_AWARD";                              //学校评奖
    /** 返回老师参与的项目的key值*/
    public static String getTeacherProjects(){
        return PROJECT_NAME + SPLIT + TEACHER_IN_PROJECT_IDS;
    }

    public static String getEventKey(){
        return PROJECT_NAME + SPLIT + QUEUE;
    }

    public static String getListShowHandleHasDoneKey(){
        return PROJECT_NAME + SPLIT + LIST_SHOW_HANDLE_HAS_DONE;
    }

    /** 中期检查老师待检查的id的key，但是这个id必须在反序列化的好确认*/
    public static String getTeacherMiddleCheckKey(int teacherId){
        return PROJECT_NAME + SPLIT + TEACHER_MIDDLE_CHECK + SPLIT + teacherId;
    }

    public static String getTeacherConcludingCheckKey(int teacherId){
        return PROJECT_NAME + SPLIT + TEACHER_CONCLUDING_CHECK + SPLIT + teacherId;
    }

    /** 获取学院评分阶段的key*/
    public static String getAcademyScoreKey(int academyUserId,int matchInfoId){
        return PROJECT_NAME + SPLIT + ACADEMY_SCORE + SPLIT + academyUserId + SPLIT + matchInfoId;
    }

    public static String getSuperScoreKey(int superUserId,int matchInfoId){
        return PROJECT_NAME + SPLIT + SUPER_SCORE + SPLIT + superUserId + SPLIT + matchInfoId;
    }

    /** 存储该比赛中获奖的比赛名单，主要存储id*/
    public static String getSuperAwardKey(int matchInfoId){
        return PROJECT_NAME + SPLIT + SUPER_AWARD + SPLIT + matchInfoId;
    }
}
