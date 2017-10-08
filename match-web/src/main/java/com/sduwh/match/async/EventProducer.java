package com.sduwh.match.async;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.jedis.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-8-26.
 * 用于将事件发送出去的服务
 * 使用redis中的阻塞队列，命令blpop,brpop,表示阻塞式取数据
 */
@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public void addEvent(EventModel eventModel){
        try {
            String event = JSONObject.toJSONString(eventModel);
            jedisAdapter.addEvent(event);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public void sendMail(String email,String title,String msg){
        EventModel eventModel = new EventModel();
        eventModel.setEventType(EventType.MAIL);
        //如果要发送mail，需要指定email,title,to
        eventModel.extAdd("email",email);
        eventModel.extAdd("title",title);
        eventModel.extAdd("msg",msg);
        addEvent(eventModel);
    }

}
