package com.sduwh.match.enums;

/**
 * Created by qxg on 17-9-14.
 */
public enum TransferMemberStage {

    ALL_DONE(0,"均通过"),
    STUDENT_ALTER(1,"学生修改阶段"),
    TEACHER_CHECK(2,"教师审核阶段"),
    ACADEMY_CHECK(3,"学院审核阶段"),
    SUPER_CHECK(4,"学校审核阶段"),
    TEACHER_NOT_PASS(-2,"教师审核未通过"),
    ACADEMY_NOT_PASS(-3,"学院审核未通过"),
    SUPER_NOT_PASS(-4,"学校审核未通过");

    int code;
    String des;

    TransferMemberStage(int code,String des){
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
