package com.sduwh.match.Enum;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by qxg on 17-6-29.
 * 表示的阶段的所有阶段
 */
public enum StageEnum {

    APPLY(10,"立项申请"),
    GUIDER_VERIFY(20,"指导老师审核"),
    ACADEMY_VERIFY(30,"学院审核"),
    SCHOOL_VERIFY(40,"学校审核"),
    SHOW(50,"名单公布"),
    RESEARCH_LOG(60,"科研日志"),
    SUPPLY_APPLY(70,"补充申报"),
    MIDDLE_CHECK(80,"中期检查"),
    CONCLUSION_CHECK(90,"结题检查"),
    AWARD(100,"学校评奖"),
    END_SHOW(110,"最终结果公布");

    private Integer id;   //标记顺序,值越小，越靠前
    private String des;
    StageEnum(Integer id,String des){
        this.id = id;
        this.des = des;
    }

    public Integer getId() {
        return id;
    }

    public String getDes() {
        return des;
    }
}
