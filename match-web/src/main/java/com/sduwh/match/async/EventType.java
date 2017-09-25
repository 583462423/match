package com.sduwh.match.async;

/**
 * Created by qxg on 17-8-26.
 */
public enum EventType {
    MAIL(1),
    STAGE_LIST_SHOW(2);

    private int value;

    EventType(int value){
        this.value = value;
    }
}
