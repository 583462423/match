package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.Roles;
import com.sduwh.match.exception.base.BaseException;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.Apply;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.PersonalMatchInfo;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** 用于下载申请表等内容的控制器*/
@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

    @Autowired
    HostHolder hostHolder;
    @Autowired
    ApplyService applyService;
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;
    @Autowired
    MatchItemService matchItemService;
    @GetMapping("/apply/{itemId}/{applyId}")
    public void getApply(@PathVariable("itemId")int itemId, @PathVariable("applyId")int applyId, final HttpServletResponse response) throws Exception {
        User user = hostHolder.getUser();
        if (user == null)throw new BaseException("未登陆，不能进行下载文件");
        //判断当前用户是否是学生，如果是学生不允许随便下载
        if(user.getRole().getId() == Roles.STUDENT.getId()){
            //判断当前用户是否参与了该比赛
            List<PersonalMatchInfo> list = personalMatchInfoService.selectByUserId(user.getId());
            boolean res = list.stream().anyMatch(p->{
                return p.getMatchItemId() == itemId;
            });
            if(!res){
                //参与过该比赛，那么就可以下载该比赛下对应的apply
                throw new BaseException("无权下载");
            }
        }

        MatchItem matchItem = matchItemService.selectByPrimaryKey(itemId);
        if(matchItem.getApplyId() != applyId)throw new BaseException("无权下载");
        //获取文件路径
        Apply apply = applyService.selectByPrimaryKey(applyId);
        String pos = apply.getPos();
        String fileName = apply.getName();
        File file = new File(pos);
        downDoc(fileName,file,response);
    }


    /** 下载名未fileName的doc文件*/
    private void downDoc(String fileName,File file,HttpServletResponse response) throws IOException {
        if(file.exists()){
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            // 防止中文乱码
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            FileUtils.copyFile(file,response.getOutputStream());
        }else throw new BaseException("不存在");
    }
}
