package com.sduwh.match.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

/**
 * Created by qxg on 17-8-26.
 */
@Service
public class MailSender implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl sender;
    @Override
    public void afterPropertiesSet() throws Exception {
        sender = new JavaMailSenderImpl();
        sender.setUsername("qinxiaoguang95@163.com");
        sender.setPassword("a123456789");
        sender.setHost("smtp.163.com");
        sender.setPort(25);
        sender.setProtocol("smtp");
        sender.setDefaultEncoding("utf8");
    }

    public void send(String to,String title,String msg)  {
        try {
            System.out.println("开始向 [" + to + "] 发送:" + title + "," + msg);
            InternetAddress from = new InternetAddress( "<qinxiaoguang95@163.com>");
            MimeMessageHelper helper = new MimeMessageHelper(sender.createMimeMessage());
            helper.setTo("583462423@qq.com");
            helper.setSubject(title);
            helper.setFrom("qinxiaoguang95@163.com");
            helper.setText(msg,true);
            sender.send(helper.getMimeMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

}
