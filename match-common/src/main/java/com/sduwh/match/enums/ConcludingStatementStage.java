package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-13.
 */
public enum ConcludingStatementStage {

    ALL_DONW(0,"均已完成"),
    STUDENT_JUDGE(1,"学生评价"),
    TEACHER_JUDGE(2,"教师评价"),
    ACADEMY_JUDGE(3,"学院评价"),
    SUPER_JUDGE(4,"学校评价"),
    ACADEMY_SCORE(5,"学院评分"),
    SUPER_SCORE(6,"学校评分");
    private int code;
    private String des;
    ConcludingStatementStage(int code, String des){
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
