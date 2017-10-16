package com.sduwh.match.controller.admin;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.entity.Notice;
import com.sduwh.match.service.notice.NoticeService;
import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.TimestampFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 超级管理员的通告管理*/
@Controller
@RequestMapping("/admin")
public class AdminNoticeController extends BaseController{

    private static final String NOTICE_ALL = "/admin/notices";
    private static final String SEND_NOTICE = "/admin/send_notice";  //发布通告
    @Autowired
    NoticeService noticeService;
    /** 获取所有的通告*/
    @GetMapping("/notice/all")
    public String allNotice(Model model){
        List<Notice> noticeList = noticeService.selectAll();
        model.addAttribute("notices",noticeList);
        return NOTICE_ALL;
    }

    @GetMapping("/notice/send")
    public String sendNoticePage(){
        return SEND_NOTICE;
    }

    @PostMapping("/send/notice")
    @ResponseBody
    public String  sendNotice(@RequestParam("title")String title,
                              @RequestParam("content")String content,
                              @RequestParam("level") int level,
                              @RequestParam("startTime")String startTime,
                              @RequestParam("endTime")String endTIme){
        if(StringUtils.nullOrEmpty(title,content,String.valueOf(level),startTime,endTIme)){
            return setJsonResult("error","输入不得有空");
        }
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setStartTime(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd hh:mm",startTime));
        notice.setEndTime(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd hh:mm",endTIme));
        notice.setLevel(level);
        notice.setIsValid(1);

        noticeService.insert(notice);
        return setJsonResult("success","true");
    }
}
