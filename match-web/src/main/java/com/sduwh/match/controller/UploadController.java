package com.sduwh.match.controller;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.Config;
import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.UploadFileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
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
    @Autowired
    StageService stageService;
    @Autowired
    UserService userService;

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
            return setJsonResult("error","只允许上传word格式的文件");
        } else if(file.getSize() > APPLY_UPLOAD_MAX_SIZE){
            return setJsonResult("error","文件过大！最大大小为:" + APPLY_UPLOAD_MAX_SIZE/1024/1024 + "MB");
        }
        //如果是其他情况，可以进行上传s
        try {
            String[] tmp = fileName.split("\\.");
            String suffix = tmp[tmp.length-1];

            String name = UploadFileNameUtils.getApplyFileName(hostHolder.getUser().getUsername(),itemId,suffix);
            File inputFile = new File(Config.APPLY_PATH + File.separator + name);
            file.transferTo(inputFile);
            //上传成功，就填写申请表,这个地方应该开启一个事务，以后再说吧
            String pos = Config.APPLY_PATH + File.separator + name;
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
                matchItemService.updateByPrimaryKeySelective(matchItem);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return setJsonResult("error","上传失败，请联系管理员");
        } catch (IOException e) {
            e.printStackTrace();
            return setJsonResult("error","上传失败，请联系管理员");
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

        //对于任何一个方法，都需要防止有人通过爬虫的方式去上传数据，这种方式可以绕过浏览器的判断，
        //所以一般来说，这个地方依然需要检查任何不符合条件的输入，检查的成本太高了。
        //比如说，输入的title是空，输入的matchInfoId为空，输入的matchInfoId不符合条件，成员数量过多等等。条件过多
        if(StringUtils.nullOrEmpty(title,String.valueOf(matchInfoId),teacherIdsString,memberIdsString,memberJob)){
            return setJsonResult("error","输入不得有空！请检查您的输入");
        }
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchInfoId);
        if(matchInfo == null){
            return setJsonResult("error","警告警告！系统发生故障！");
        }else{
            //判断当前的申请阶段是否符合时间条件，如果不符合，不让上传
            //一般来说，第一个阶段就是申请阶段，如果不是，不让上传！
            String allStage = matchInfo.getAllStage();
            if(StringUtils.nullOrEmpty(allStage))return setJsonResult("error","该比赛禁止申请！");
            else{
                String[] allStageArray = allStage.split(",");
                if(allStageArray == null || allStageArray.length == 0)return setJsonResult("error","该比赛禁止申请!");
                else{
                    Stage stage = stageService.selectByPrimaryKey(Integer.parseInt(allStageArray[0]));
                    if(stage.getStageFlag() != MatchStage.APPLY.getId())return setJsonResult("error","该比赛禁止申请！");
                    else{
                        if(!stageService.checkIsRuning(stage)) return setJsonResult("error","比赛已过申请时间，禁止申请");
                    }
                }
            }
        }

        //判断成员数量，指导老师数量，是否符合条件
        if(memberIdsString.split(",").length > matchInfo.getMemberNum()
                || teacherIdsString.split(",").length > matchInfo.getTeacherNum()){
            return setJsonResult("error","数量超出范围!");
        }
        Map<String ,String> res = new HashMap<>();
        List<Integer> members = Arrays.stream(memberIdsString.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<String> memberJobs = Arrays.stream(memberJob.split(",")).collect(Collectors.toList());
        if(members.size() != memberJobs.size()){
            return setJsonResult("error","分工中不能键入逗号(\",\"),如果有，请删除");
        }
        members.add(hostHolder.getUser().getId());
        memberJobs.add("队长");
        Arrays.stream(teacherIdsString.split(",")).map(Integer::parseInt).forEach(id->{
            members.add(id);
            memberJobs.add("指导教师");
        });

        MatchItem matchItem = new MatchItem();
        matchItem.setLeaderIds(hostHolder.getUser().getId().toString());
        matchItem.setMatchInfoId(matchInfoId);
        matchItem.setMemberIds(memberIdsString);
        matchItem.setTeacherIds(teacherIdsString);
        matchItem.setTitle(title);

        //设置当前的阶段，当前阶段应该是上传申请表，即info中stage的第一个
        String stagesString = matchInfo.getAllStage();

        matchItem.setNowStageId(Integer.parseInt(matchInfo.getAllStage().split(",")[0]));

        //设置比赛的学院，学院应当是和学生的学院信息一致
        matchItem.setAcademyId(hostHolder.getUser().getAcademyId());

        //添加前判断当前的用户的是否符合要求，比如教师指导的数量是否超过，队长参加的比赛数量是否超过，成员参加的比赛数量是否超过等。
        //注意一般来说，队长参加比赛的数量指的是该用户作为队长参加比赛的数量
        //还需要判断上传的数据中，教师是否是教师，成员是否是成员等等

        //首先是队长是否符合要求 start
        Integer leaderId = Integer.valueOf(matchItem.getLeaderIds());
        long leaderCount = personalMatchInfoService.selectByUserId(leaderId).stream().filter(p->{
            //过滤为队长的比赛
            MatchItem m = matchItemService.selectByPrimaryKey(p.getMatchItemId());
            if(m.getMatchInfoId() != matchItem.getMatchInfoId())return false;
            else{
                return m.getLeaderIds().equals(matchItem.getLeaderIds());
            }
        }).count();
        if(leaderCount >= matchInfo.getLeaderInNum())
            return setJsonResult("error","队长参加比赛超过负荷！");
        // end

        //接着是每个成员符合要求
        String[] memberIds = memberIdsString.split(",");
        for(int i=0; i<memberIds.length; i++){
            int id = Integer.parseInt(memberIds[i]);
            //参与比赛的个数
            long cnt = personalMatchInfoService.selectByUserId(id).stream().filter(p->{
                MatchItem  m = matchItemService.selectByPrimaryKey(p.getMatchItemId());
                return m.getMatchInfoId() == matchItem.getMatchInfoId();
            }).count();
            if(cnt >= matchInfo.getMemberInNum()){
                User user = userService.selectByPrimaryKey(id);
                return setJsonResult("error","成员 " + user.getName() + " 参加比赛超过负荷！！");
            }
        }

        //指导老师指导的数量
        String[] teacherIds = teacherIdsString.split(",");
        for(int i=0; i<teacherIds.length; i++){
            int id = Integer.parseInt(teacherIds[i]);
            //参与比赛的个数
            long cnt = personalMatchInfoService.selectByUserId(id).stream().filter(p->{
                MatchItem  m = matchItemService.selectByPrimaryKey(p.getMatchItemId());
                return m.getMatchInfoId() == matchItem.getMatchInfoId();
            }).count();
            if(cnt >= matchInfo.getTeacherInNum()){
                User user = userService.selectByPrimaryKey(id);
                return setJsonResult("error","教师 " + user.getName() + " 指导比赛数量超过负荷！！");
            }
        }


        matchItemService.insert(matchItem);
        for(int i=0; i<members.size(); i++){
            int id = members.get(i);
            PersonalMatchInfo info = new PersonalMatchInfo();
            info.setUserId(id);
            info.setJobAssignment(memberJobs.get(i));
            info.setMatchItemId(matchItem.getId());
            personalMatchInfoService.insert(info);
        }

        return setJsonResult("itemId",String.valueOf(matchItem.getId()),"success","true");
    }
}
