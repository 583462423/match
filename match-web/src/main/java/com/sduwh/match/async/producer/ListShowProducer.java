package com.sduwh.match.async.producer;

import com.sduwh.match.async.EventModel;
import com.sduwh.match.async.EventProducer;
import com.sduwh.match.async.EventType;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.jedis.RedisKeyGenerator;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.stage.StageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by qxg on 17-9-6.
 * 专门用于处理名单公布阶段的 生成器，开启一个线程，去判断哪个比赛的名单公布阶段已经开始，
 * 如果开始，把这个stage加到set中，并对所有参与比赛的人员发送email.
 */
@Service
public class ListShowProducer implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(ListShowProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    StageService stageService;
    @Autowired
    EventProducer eventProducer;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this::startThread).start();
    }

    private void startThread(){
        //无限循环，寻找达到名单公布阶段的stage
        String key = RedisKeyGenerator.getListShowHandleHasDoneKey();
        for(;;){
            List<Stage> stages = stageService.selectByStageFlag(MatchStage.SHOW.getId());
            System.out.println(stages);
            stages.stream()
                    .filter(s->{
                        //过滤已经被处理的stage
                        return !jedisAdapter.sismember(key,s.getId().toString());
                    })
                    .filter(s->{
                        //过滤未到时间的stage
                        return s.getStartTime().before(new Date());
                    })
                    .forEach(s->{
                        //剩下的是当前符合条件的，转换为事件，传递出去
                        System.out.println("符合条件的stage : "  + s);
                        EventModel eventModel = new EventModel();
                        eventModel.setEventType(EventType.STAGE_LIST_SHOW);
                        eventModel.extAdd("stage",s);
                        eventProducer.addEvent(eventModel);
                        jedisAdapter.sadd(key,s.getId().toString());
                    });

            //沉睡1分钟后，再进行循环执行，减轻系统负担
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
