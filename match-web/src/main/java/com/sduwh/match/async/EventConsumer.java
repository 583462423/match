package com.sduwh.match.async;

import com.alibaba.fastjson.JSON;
import com.sduwh.match.jedis.JedisAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qxg on 17-8-26.
 * 事件消费者
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    /** 标记eventType和eventhandler的映射信息*/
    private Map<EventType,List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        beans.forEach((key,value)->{
            //对于每个EventHandler而言，都有一个Supprot的列表，便利列表，添加到config中
            value.getSupportEventTypes().forEach(e->{
                List<EventHandler> list = config.get(e);
                if(list == null)
                    list = new ArrayList<>();
                list.add(value);
                config.put(e,list);
            });
        });


        //配置完成后，就开启线程异步执行
        new Thread(()->{
            for(;;){
                jedisAdapter.getEvent().forEach(e->{
                    EventModel eventModel = JSON.parseObject(e,EventModel.class);
                    List<EventHandler> list = config.get(eventModel.getEventType());
                    if(list != null){
                        list.forEach(handler->{
                            handler.doHandle(eventModel);
                        });
                    }
                });
            }
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
