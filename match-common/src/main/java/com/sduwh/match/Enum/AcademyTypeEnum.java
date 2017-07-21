package com.sduwh.match.Enum;

/**
 * Created by qxg on 17-7-20.
 */
public enum  AcademyTypeEnum {

    /** English is v............ery poor ,so use ChinesePINYIN!Sorry*/
    LIGONG(1,"理工科"),
    WENKE(2,"文科");

    private Integer id;
    private String type;

    AcademyTypeEnum(Integer id,String type){
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
