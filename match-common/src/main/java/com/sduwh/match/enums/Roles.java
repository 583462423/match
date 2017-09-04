package com.sduwh.match.enums;

/**
 * Created by qxg on 17-6-29.
 * 保存完整角色信息，应该是用不到= =
 */
public enum  Roles {
    ADMIN(1,"admin","超级管理员"),
    ACADEMY_ADMIN(2,"academy_admin","学院管理员"),
    GUIDER(3,"guider","指导老师"),
    RATER(4,"rater","临时评委"),
    STUDENT(5,"student","学生");

    int id;
    String name;
    String des;

    Roles(int id,String name,String des){
        this.id = id;
        this.name = name;
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }
}

