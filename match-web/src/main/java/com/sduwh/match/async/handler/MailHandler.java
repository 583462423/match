package com.sduwh.match.async.handler;


import com.sduwh.match.async.EventHandler;
import com.sduwh.match.async.EventModel;
import com.sduwh.match.async.EventType;
import com.sduwh.match.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qxg on 17-8-26.
 * 这个是用来测试的。
 */
@Service
public class MailHandler implements EventHandler {

    @Autowired
    MailSender sender;
    @Override
    public void doHandle(EventModel eventModel) {
        //这里开始发邮件'

        sender.send("583462423@qq.com","放飞梦想","亲爱的用户您好，您在本网站进行了消费10000元，验证码为9483,请尽快进行确认。");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        List<EventType> list = new ArrayList<>();
        list.add(EventType.MAIL);
        return list;
    }
}
