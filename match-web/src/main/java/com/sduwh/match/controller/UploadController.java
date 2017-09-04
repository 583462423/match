package com.sduwh.match.controller;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.Apply;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.PersonalMatchInfo;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-8-29.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final int APPLY_UPLOAD_MAX_SIZE = 5*1024*1024; //申请表最大限制5MB。
    private static final String APPLY_PATH = "/home/qxg/webApplication/match/match-web/upload/apply";

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MatchItemService matchItemService;

    @Autowired
    MatchInfoService matchInfoService;

    @Autowired
    ApplyService applyService;

    @Autowired
    PersonalMatchInfoService personalMatchInfoService;

    /** 申请表上传，这个地方也要有其他信息，否则无法实现控制*/
    @PostMapping("/match/apply/{id}")
    @ResponseBody
    @Transactional
    public String uploadApply(@RequestParam("file") MultipartFile file,
            @PathVariable("id")int itemId,
            HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        Map<String,String> info = new HashMap<>();
        MatchItem matchItem = matchItemService.selectByPrimaryKey(itemId);
        if(!fileName.endsWith(".doc") && !fileName.endsWith(".docx")){
            info.put("error","只允许上传word格式的文件");
            return JSONObject.toJSONString(info);
        } else if(file.getSize() > APPLY_UPLOAD_MAX_SIZE){
            info.put("error","文件过大！");
            return JSONObject.toJSONString(info);
        }
        //如果是其他情况，可以进行上传s
        try {
            String[] tmp = fileName.split("\\.");
            String suffix = tmp[tmp.length-1];

            String name = MD5Utils.md5(hostHolder.getUser().getName() + itemId) + "." + suffix;
            File inputFile = new File(APPLY_PATH + File.separator + name);
            file.transferTo(inputFile);
            //上传成功，就填写申请表,这个地方应该开启一个事务，以后再说吧
            String pos = APPLY_PATH + File.separator + name;
            if(matchItem.getApplyId()!=null){
                //如果apply不为空，就更新
                Apply apply = new Apply();
                apply.setId(matchItem.getApplyId());
                apply.setPos(pos);
                applyService.updateByPrimaryKeySelective(apply);
            }else{
                Apply apply = new Apply();
                apply.setLeaderId(hostHolder.getUser().getId());
                apply.setMemberIds(matchItem.getMemberIds());
                apply.setName(fileName);
                apply.setPos(pos);
                apply.setTeacherIds(matchItem.getTeacherIds());
                applyService.insert(apply);
                matchItem.setApplyId(apply.getId());
                matchItemService.updateByPrimaryKey(matchItem);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return JSONObject.toJSONString(info);
    }

    /** 上传申请表，但是只是上传文字信息
     * title 项目标题
     * matchInfoId 是申请的比赛的id
     * teacherIdsString是指定的老师的id，这个参数和下面的参数都是形如 1,2,3的形式。
     * memberIdsString是指定的学生的id
     * */
    @PostMapping("/match/apply/form")
    @ResponseBody
    @Transactional
    public String submitApply(@RequestParam("title")String title,
                              @RequestParam("matchInfoId")int matchInfoId,
                              @RequestParam("teacherIds")String teacherIdsString,
                              @RequestParam("memberIds")String memberIdsString,
                              @RequestParam("memberJob")String memberJob){
        //TODO 在JSP分工中，要限制使用','，因为在这个地方要进行分割
        Map<String ,String> res = new HashMap<>();
        List<Integer> members = Arrays.stream(memberIdsString.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<String> memberJobs = Arrays.stream(memberJob.split(",")).collect(Collectors.toList());
        if(members.size() != memberJobs.size()){
            res.put("error","分工中不能键入逗号(\",\"),如果有，请删除");
            return JSONObject.toJSONString(res);
        }
        members.add(hostHolder.getUser().getId());
        memberJobs.add("队长");
        MatchItem matchItem = new MatchItem();
        matchItem.setLeaderIds(hostHolder.getUser().getId().toString());
        matchItem.setMatchInfoId(matchInfoId);
        matchItem.setMemberIds(memberIdsString);
        matchItem.setTeacherIds(teacherIdsString);
        matchItem.setTitle(title);

        //设置当前的阶段，当前阶段应该是上传申请表，即info中stage的第一个
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchInfoId);
        matchItem.setNowStageId(Integer.parseInt(matchInfo.getAllStage().split(",")[0]));
        matchItemService.insert(matchItem);


        for(int i=0; i<members.size(); i++){
            int id = members.get(i);
            PersonalMatchInfo info = new PersonalMatchInfo();
            info.setUserId(id);
            info.setJobAssignment(memberJobs.get(i));
            info.setMatchItemId(matchItem.getId());
            personalMatchInfoService.insert(info);
        }

        res.put("itemId",String.valueOf(matchItem.getId()));
        res.put("success","true");
        return JSONObject.toJSONString(res);
    }
}
