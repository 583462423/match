package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 用于下载申请表等内容的控制器*/
@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

    @GetMapping("/apply/{itemId}")
    public String getApply(@PathVariable("itemId")int itemId){
        return null;
        //考虑不用下载，用在线预览
    }
}
