package com.sduwh.match.async.handler;

import com.alibaba.fastjson.JSON;
import com.sduwh.match.Config;
import com.sduwh.match.async.EventHandler;
import com.sduwh.match.async.EventModel;
import com.sduwh.match.async.EventType;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.MailSender;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by qxg on 17-9-6.
 * 名单公布处理器
 */
@Service
public class StageListShowEventHandler implements EventHandler{

    private static final Logger logger = LoggerFactory.getLogger(StageListShowEventHandler.class);
    @Autowired
    StageService stageService;
    @Autowired
    MailSender sender;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    UserService userService;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void doHandle(EventModel eventModel) {
        //TODO 这个地方有BUG，eventModel在反序列化后，因map反序列化失败，所以需要DEBUG，回头看一下
        Stage stage = JSON.parseObject(JSON.toJSONString(eventModel.extGet("stage")),Stage.class) ;
        if(stage == null)return;
        if(!stageService.checkIsRuning(stage))return;
        //TODO 对当前的stage进行处理
        logger.info("当前状态" + stage + "正在运行，该状态名单准备公布");
        //这个地方要发送邮件，所以通过状态来获取比赛信息，再通过比赛信息，获取参与人员
        List<MatchItem> list = matchItemService.selectByNowStageId(stage.getId());
        if(list.isEmpty() || list.size() == 0)return;

        Set<Integer> ids = new HashSet<Integer>();
        String title = "山东大学(威海)比赛：" + matchInfoService.selectByPrimaryKey(list.get(0).getMatchInfoId()).getName() + " 名单公布";   //发送邮件的标题
        String msg = "请到以下链接处查看公布名单～";     //发送邮件的内容
        msg = msg + "\n" + Config.URL + "/match/show/" + stage.getId() + "\n";
        list.stream().forEach(item->{
            //通过item获取memberid,leaderid等
            Arrays.stream(item.getLeaderIds().split(",")).map(Integer::parseInt).forEach(ids::add);
            Arrays.stream(item.getMemberIds().split(",")).map(Integer::parseInt).forEach(ids::add);
            Arrays.stream(item.getTeacherIds().split(",")).map(Integer::parseInt).forEach(ids::add);
        });

        String finalMsg = msg;
        ids.stream().forEach(id->{
            //通过id获取对应的user,然后给user对应的email发送信息
            User user = userService.selectByPrimaryKey(id);
            String email = user.getEmail();
            if(email.isEmpty() || email == null)return;
            sender.send(email,title, finalMsg);
        });
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        List<EventType> list = new ArrayList<>();
        list.add(EventType.STAGE_LIST_SHOW);
        return list;
    }
}
