package com.sduwh.match.async.handler;


import com.sduwh.match.async.EventHandler;
import com.sduwh.match.async.EventModel;
import com.sduwh.match.async.EventType;
import com.sduwh.match.service.MailSender;
import com.sduwh.match.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qxg on 17-8-26.
 * 发送邮件，如果要发送邮件，必须指定发送的目标用户，主题，和消息即email,title,msg,如果有不符合的就不发送
 */
@Service
public class MailHandler implements EventHandler {

    @Autowired
    MailSender sender;
    @Override
    public void doHandle(EventModel eventModel) {
        //这里开始发邮件'
        String email = (String) eventModel.extGet("email");
        String msg = (String) eventModel.extGet("msg");
        String title = (String) eventModel.extGet("title");
        if(StringUtils.nullOrEmpty(email,msg,title)){
            System.out.println("邮件发送失败～～～～～");
            return;
        }

        sender.send(email,title,msg);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        List<EventType> list = new ArrayList<>();
        list.add(EventType.MAIL);
        return list;
    }
}
