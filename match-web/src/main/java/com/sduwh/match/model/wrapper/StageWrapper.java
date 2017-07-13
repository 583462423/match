package com.sduwh.match.model.wrapper;

import com.sun.tools.corba.se.idl.constExpr.Times;

import java.sql.Timestamp;

/**
 * Created by qxg on 17-6-30.
 */
public class StageWrapper {
    private String stageName;
    private Integer id;         //标记是阶段的哪种 StageEnum中的id
    private Boolean isChoose;   //标记此种类型的阶段是否被选中
    private Integer type;       //标记是否按照时间走
    private Timestamp startTime;//标记开始时间
    private Timestamp endTime;  //标记结束时间

    public StageWrapper(){}
    public StageWrapper(String stageName, Integer id, Boolean isChoose) {
        this.stageName = stageName;
        this.id = id;
        this.isChoose = isChoose;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getChoose() {
        return isChoose;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
