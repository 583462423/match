package com.sduwh.match.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxg on 17-8-26.
 * 事件模型，具体事件自己具体进行实现
 */
public class EventModel  {
    private EventType eventType;
    private Map<String,Object> ext;

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public EventModel(){
        ext = new HashMap<>();
    }
    public EventType getEventType() {
        return eventType;
    }

    public  void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void extAdd(String s,Object o){
        ext.put(s,o);
    }

    public Object extGet(String s){
        return ext.get(s);
    }
}
