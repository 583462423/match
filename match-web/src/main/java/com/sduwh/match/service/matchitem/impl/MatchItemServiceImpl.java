package com.sduwh.match.service.matchitem.impl;

import com.alibaba.fastjson.JSONObject;
import com.sduwh.match.async.EventModel;
import com.sduwh.match.async.EventProducer;
import com.sduwh.match.async.EventType;
import com.sduwh.match.dao.MatchItemMapper;
import com.sduwh.match.enums.*;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.*;
import com.sduwh.match.model.wrapper.MatchItemDetail;
import com.sduwh.match.model.wrapper.MatchItemWithScore;
import com.sduwh.match.service.academy.AcademyService;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.JSONResponseUtils;
import com.sduwh.match.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-7-12.
 */
@Service
public class MatchItemServiceImpl implements MatchItemService {

    @Autowired
    MatchItemMapper matchItemMapper;
    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    StageService stageService;
    @Autowired
    PassService passService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    GradeService gradeService;
    @Autowired
    AcademyService academyService;
    @Autowired
    ApplyService applyService;
    @Autowired
    UserService userService;
    @Autowired
    EventProducer eventProducer;

    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return matchItemMapper.deleteByPrimaryKey(integer);
    }

    @Override
    public int insert(MatchItem record) {
        return matchItemMapper.insert(record);
    }

    @Override
    public int insertSelective(MatchItem record) {
        return matchItemMapper.insertSelective(record);
    }

    @Override
    public MatchItem selectByPrimaryKey(Integer integer) {
        //获取比赛条目，因为涉及到状态信息，所以每次获取，都要进行查看状态信息是否已经过期，如果已经过期，那么就需要进行调整
        MatchItem matchItem = matchItemMapper.selectByPrimaryKey(integer);
        if(matchItem.getByTime() == MatchByTimeEnum.NO.getCode()){
            //如果指明不按照时间格式来，就直接返回
            //这里还要进行判断，如果当前阶段已经可以正常运行，则按照正常方式来执行
            Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
            if(stageService.checkIsRuning(stage)){
                matchItem.setByTime(MatchByTimeEnum.YES.getCode());
                updateByPrimaryKeySelective(matchItem);
            }
            return matchItem;
        }
        if(matchItem == null)return null;
        //TODO matchItem也可能为空，原因也是后期删除
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        //TODO 注意这个地方matchInfo可能为空，原因是管理员将这个比赛删除，所以需要后续的工作是，管理员删除比赛的时候，将所有的相关记录都要删除掉，否则有些地方容易报错
        if(matchInfo == null){
            //如果matchInfo为空，这个比赛就不应该存在，所以考虑是否将该比赛删除
            //但实际上不需要删除，为了方便以后统计
            //其实，删除这个东西实质上不存在的，在企业中，一般不会删除数据，只会给数据打一个标记，标记该数据不可用，这样方便以后统计，不然大数据中的数据是从哪来的，数据是最值钱的东西
            //但是项目大体设计成了这样，如果要设计成标记删除，修改的地方就有很多。所以正确的操作是，管理员尽量不去做删除操作。
            return null;
        }
        String[] stageIds = matchInfo.getAllStage().split(",");
        int stageId = matchItem.getNowStageId();
        int nextStageId = MatchStatus.ALL_DONE.getValue();
        if(stageId < 0){
            //如果状态小于0,说明是非正常比赛，直接将结果显示出来即可
            return matchItem;
        }
        for(int i=0; i<stageIds.length; i++){
            int tmpStage = Integer.parseInt(stageIds[i]);
            if(stageId == tmpStage && i!= stageIds.length-1){
                nextStageId = Integer.parseInt(stageIds[i+1]);
                break;
            }
        }

        //判断当前的stageId是否已经超时,stageId可能是-1,所以stage可能为空
        Stage stage = stageService.selectByPrimaryKey(stageId);

        if (stage!=null && stageService.checkEnd(stage)){
            //当前状态已过期，那么就需要重新设置状态,即更新操作
            //当前状态需要根据实际情况进行判断，比如当前状态为审核，需要取得审核表，如果没有，说明未通过，直接将状态设置为-2.
            //但是一般来说，审核成功的都会有审核表，并且设置为下一个阶段，所以阶段控制不需要进行，如果当前为审核阶段，直接设置为审核不通过
            nextStageId = getNextStage(stage,matchItem,nextStageId);
            matchItem.setNowStageId(nextStageId);
            updateByPrimaryKey(matchItem);
            //更新完毕，重新调用该方法,目的就是防止下一个阶段依然是无效期
            return selectByPrimaryKey(integer);
        }else{
            return matchItemMapper.selectByPrimaryKey(integer);
        }
    }


    /** 根据当前状态，获取比赛的下一个状态是什么，注意调用这个方法的前提是，当前的状态已过期*/
    private int getNextStage(Stage stage,MatchItem matchItem,int nextStage){

        int stageFlag = stage.getStageFlag();
        //有时候不是我不用swtich而是这个地方的逻辑不允许我使用switch，so,如果有人在维护代码的时候看到这个地方的代码，请轻喷，否则，大爷您来完成他！
        if(stageFlag == MatchStage.GUIDER_VERIFY.getId()){
            //当前状态为指导老师审核，但是状态已经完成，老师也没审核，那就直接设置为审核未通过，下同
            return MatchStatus.TEACHER_NOT_PASS.getValue();
        }else if(stageFlag == MatchStage.ACADEMY_VERIFY.getId()){
            return MatchStatus.ACADEMY_NOT_PASS.getValue();
        }else if(stageFlag == MatchStage.SCHOOL_VERIFY.getId()){
            return MatchStatus.SUPER_NOT_PASS.getValue();
            //以上的代码执行完毕的话，如果审核阶段到时了
        }

        //如果没有符合条件的，就下一个状态吧
        return nextStage;

    }

    @Override
    public int updateByPrimaryKeySelective(MatchItem record) {
        return matchItemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchItem record) {
        return matchItemMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatchItem> selectAllByStringIds(String ids) {
        if (StringUtils.nullOrEmpty(ids))return null;
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return idList.stream().map(this::selectByPrimaryKey).collect(Collectors.toList());
    }

    @Override
    public List<MatchItem> selectAllByInfoId(Integer id) {
        //只要是select开头的，都不能调用mapper的，因为selectByPrimary坐了特殊处理，必须调用那个方法
        List<MatchItem> matchItems = matchItemMapper.selectAllByInfoId(id);
        return matchItems.stream().map(item->{
            //每个搜到的matchItem,都要重新调用selectByPrimaryKey方法进行重加工
            return selectByPrimaryKey(item.getId());
        }).collect(Collectors.toList());
    }

    @Override
    public List<MatchItem> selectByStageId(int stageId) {
        List<MatchItem> matchItems = matchItemMapper.selectByStageId(stageId);
        return matchItems.stream().map(item->{
            //每个搜到的matchItem,都要重新调用selectByPrimaryKey方法进行重加工
            return selectByPrimaryKey(item.getId());
        }).collect(Collectors.toList());
    }



    @Override
    public int updateWhenTeacherAllChecked(int id) {
        MatchItem matchItem = selectByPrimaryKey(id);
        int nowStage = matchItem.getNowStageId();
        Stage stage = stageService.selectByPrimaryKey(nowStage);
        if(stage.getStageFlag() != MatchStage.GUIDER_VERIFY.getId())throw new RuntimeException("当前状态不是教师状态");


        //判断当前状态中的所有老师是否都已经审核完毕，如果是，就更新当前状态
        if(Arrays.stream(matchItem.getTeacherIds().split(","))
                .map(Integer::parseInt)
                .allMatch(e->{
                    Pass pass = passService.selectByUserAndItem(e,matchItem.getId());
                    return passService.checkPass(pass);
                })
                ){
            //如果所有老师都审核通过了
            //更新状态
            return updateAndSetNextStage(matchItem,stage);
        }

        return 0;
    }

    @Override
    public String checkDetail(Model model,int id) {
        //通过比赛id来查找比赛信息
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        model.addAttribute("detail",selectDetailById(matchItem.getId()));
        //最重要的是把id传进去，方便后续进行审核控制
        model.addAttribute("itemId",matchItem.getId());
        //判断当前是否已经被审核过了
        Pass pass = passService.selectByUserAndItem(hostHolder.getUser().getId(),matchItem.getId());
        if(passService.checkPass(pass)){
            //如果被审核过
            model.addAttribute("pass","true");
        }
        return null;
    }

    @Override
    public String checkPass(int matchItemId,int flag) {
        //这里要考虑的一个问题是，是否添加一个审核表，因为一个比赛可能对应多个审核的教师。。= =
        //考虑完毕，需要添加审核表，毕竟审核也是一种记录。
        //每个审核表，都可以通过user_id,和match_item_id对应一条记录，这个记录标记该用户是否给该比赛通过
        MatchItem matchItem = selectByPrimaryKey(matchItemId);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(stage.getStageFlag() != MatchStage.GUIDER_VERIFY.getId() &&
                stage.getStageFlag() != MatchStage.ACADEMY_VERIFY.getId() &&
                stage.getStageFlag() != MatchStage.SCHOOL_VERIFY.getId()){
            return JSONResponseUtils.setJSONResponse("error","没有权限访问！");
        }

        Pass pass = passService.selectByUserAndItem(hostHolder.getUser().getId(),matchItemId);
        if(pass != null){
            return JSONResponseUtils.setJSONResponse("error","您已经审核过该用户");
        }
        pass = new Pass();
        pass.setUserId(hostHolder.getUser().getId());
        pass.setMatchItemId(matchItemId);
        pass.setStatus(PassStatus.PASS.getValue());
        passService.insert(pass);

        switch (flag){
            case CHECK_PASS_ACADEMY:
                //学院管理员审核成功
                updateWhenAcademyChecked(matchItemId);
                break;
            case CHECK_PASS_CAMPUS:
                updateWhenAdminCheckd(matchItemId);
                break;
            case CHECK_PASS_TEACHER:
                //判断当前审核的老师中是否还有未审核的，如果没有，就设置当前状态为下一状态
                updateWhenTeacherAllChecked(matchItemId);
                break;
        }

        return null;
    }

    @Override
    @Transactional
    public String checkNoPass(int id, int flag) {
        //首先判断是教师审核不通过，还是学院，还是学校，如果是教师审核不通过，直接将该比赛的状态重置为上传申请表阶段
        //而如果是学院，则将教师审核的审核表删除，并将比赛的状态重置为上传申请表阶段
        //而如果是学校，则删除学院，教师等，并将比赛状态重置为上传申请表阶段
        //后续可以在这里添加一个记录，表示该比赛曾经审核不通过
        String res = null;
        switch (flag){
            case CHECK_PASS_TEACHER:
                res = teacherCheckNopass(id);
                break;
            case CHECK_PASS_ACADEMY:
                res = academyCheckNopass(id);
                break;
            case CHECK_PASS_CAMPUS:
                res = adminCheckNopass(id);
                break;
        }

        return res;
    }

    /** 学校审核不通过*/
    private String adminCheckNopass(int matchItemId){
        //将MatchItem的比赛阶段重置为立项申请阶段
        MatchItem matchItem = selectByPrimaryKey(matchItemId);
        Stage nowStage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(nowStage.getStageFlag() != MatchStage.SCHOOL_VERIFY.getId()){
            return JSONResponseUtils.setJSONResponse("error","无权访问！");
        }
        if(setMatchItemToApply(matchItem)){
            //因为是学校未通过，所以需要将教师审核的数据删除，以及学院审核的表删除
            Arrays.stream(matchItem.getTeacherIds().split(","))
                    .map(Integer::parseInt)
                    .map(id-> passService.selectByUserAndItem(id,matchItemId))
                    .forEach(pass -> {
                        if(pass != null)passService.deleteByPrimaryKey(pass.getId());
                    });
            //获取学院管理员
            User academyUser = userService.selectAcademyUserByAcademyId(Roles.ACADEMY_ADMIN.getId(),matchItem.getAcademyId());
            if(academyUser != null){
                //删除学院管理员审核通过的项目
                Pass pass = passService.selectByUserAndItem(academyUser.getId(),matchItemId);
                if(pass != null) passService.deleteByPrimaryKey(pass.getId());
            }

            User user = userService.selectByPrimaryKey(Integer.valueOf(matchItem.getLeaderIds()));
            eventProducer.sendMail(user.getEmail(),"山东大学(威海)比赛通知:您的比赛项目学校未审核通过，请重新上传申请表。","您的比赛:" + matchItem.getTitle() + " 未通过学院审核，请更新并重新上传申请表");
            return null;
        }else{
            return JSONResponseUtils.setJSONResponse("error","审核不通过失败");
        }
    }

    /** 学院审核不通过*/
    private String academyCheckNopass(int matchItemId){
        //将MatchItem的比赛阶段重置为立项申请阶段
        MatchItem matchItem = selectByPrimaryKey(matchItemId);
        Stage nowStage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(nowStage.getStageFlag() != MatchStage.ACADEMY_VERIFY.getId()){
            return JSONResponseUtils.setJSONResponse("error","无权访问！");
        }
        if(setMatchItemToApply(matchItem)){
            //因为是学院未通过，所以需要将教师审核的数据删除，教师审核的时候，会生成审核表
            Arrays.stream(matchItem.getTeacherIds().split(","))
                    .map(Integer::parseInt)
                    .map(id-> passService.selectByUserAndItem(id,matchItemId))
                    .forEach(pass -> passService.deleteByPrimaryKey(pass.getId()));

            User user = userService.selectByPrimaryKey(Integer.valueOf(matchItem.getLeaderIds()));
            eventProducer.sendMail(user.getEmail(),"山东大学(威海)比赛通知:您的比赛项目学院未审核通过，请重新上传申请表。","您的比赛:" + matchItem.getTitle() + " 未通过学院审核，请更新并重新上传申请表");
            return null;
        }else{
            return JSONResponseUtils.setJSONResponse("error","审核不通过失败");
        }
    }

    /** 教师审核不通过*/
    private String teacherCheckNopass(int matchItemId){
        //将MatchItem的比赛阶段重置为立项申请阶段
        MatchItem matchItem = selectByPrimaryKey(matchItemId);
        Stage nowStage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        if(nowStage.getStageFlag() != MatchStage.GUIDER_VERIFY.getId()){
            return JSONResponseUtils.setJSONResponse("error","无权访问！");
        }
        if(setMatchItemToApply(matchItem)){
            User user = userService.selectByPrimaryKey(Integer.valueOf(matchItem.getLeaderIds()));
            eventProducer.sendMail(user.getEmail(),"山东大学(威海)比赛通知:您的比赛项目未审核通过，请重新上传申请表。","您的比赛:" + matchItem.getTitle() + " 未通过指导教师审核，请更新并重新上传申请表");
            return null;
        }else{
            return JSONResponseUtils.setJSONResponse("error","审核不通过失败");
        }
    }

    /** 设置对应的比赛到比赛申请阶段 ,true表示设置成功，false表示设置失败*/
    private boolean setMatchItemToApply(MatchItem matchItem){
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        boolean flag = Arrays.stream(matchInfo.getAllStage().split(",")).map(Integer::parseInt)
                .map(stageService::selectByPrimaryKey).anyMatch(s->{
                    if(s.getStageFlag() == MatchStage.APPLY.getId()){
                        //立项申请阶段
                        matchItem.setNowStageId(s.getId());
                        if(!stageService.checkIsRuning(s)){
                            //如果立项申请阶段没有按照正常时间来执行，则设置一个标志，标志该比赛不按时间阶段正常执行
                            matchItem.setByTime(MatchByTimeEnum.NO.getCode());
                        }
                        matchItemService.updateByPrimaryKeySelective(matchItem);
                        return true;
                    }else return false;
                });
        return flag;
    }


    @Override
    public int updateAndSetNextStage(MatchItem matchItem,Stage stage) {
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        String[] stages = matchInfo.getAllStage().split(",");
        int nowStage = matchItem.getNowStageId();

        int nextStage = MatchStatus.ALL_DONE.getValue();
        //获取下一个状态
        for(int i=0; i<stages.length; i++){
            int tmpStage = Integer.parseInt(stages[i]);
            if(nowStage == tmpStage && i!= stages.length-1){
                nextStage = Integer.parseInt(stages[i+1]);
                break;
            }
        }
        matchItem.setNowStageId(nextStage);
        return updateByPrimaryKey(matchItem);
    }

    @Override
    public List<MatchItem> selectByNowStageId(int nowStageId) {
        return matchItemMapper.selectByStageId(nowStageId);
    }

    @Override
    public boolean checkIsRunning(MatchItem matchItem) {
        Date now = new Date();
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        return matchInfo.getStartTime().before(now) && matchInfo.getEndTime().after(now);
    }

    @Override
    public List<MatchItemWithScore> selectAwardsWithScore(int matchInfoId) {
        List<MatchItemWithScore> ms = matchItemService.selectAllByInfoId(matchInfoId)
                .stream()
                .filter(matchItem -> {
                    Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
                    return stageService.checkIsRuning(stage) && stage.getStageFlag() == MatchStage.AWARD.getId();
                })
                .map(matchItem -> {
                    //获取到matchItem的分数
                    MatchItemWithScore m = new MatchItemWithScore();
                    m.setMatchItem(matchItem);
                    m.setScore(gradeService.selectAverangeByMatchItem(matchItem.getId()));
                    return m;
                })
                .sorted((m1,m2)-> (int)((m1.getScore() - m2.getScore()) * 100))
                .collect(Collectors.toList());
        return ms;
    }

    @Override
    public MatchItemDetail selectDetailById(int matchItemId) {
        MatchItemDetail detail = new MatchItemDetail();
        MatchItem matchItem = selectByPrimaryKey(matchItemId);
        List<User> members = Arrays.stream(matchItem.getMemberIds().split(",")).map(Integer::parseInt).map(userService::selectByPrimaryKey)
                .collect(Collectors.toList());
        List<User> teachers = Arrays.stream(matchItem.getTeacherIds().split(",")).map(Integer::parseInt).map(userService::selectByPrimaryKey)
                .collect(Collectors.toList());
        detail.setMatchItem(matchItem);
        detail.setAcademy(academyService.selectByPrimaryKey(matchItem.getAcademyId()));
        detail.setApply(applyService.selectByPrimaryKey(matchItem.getApplyId()));
        detail.setLeader(userService.selectByPrimaryKey(Integer.valueOf(matchItem.getLeaderIds())));
        detail.setMembers(members);
        detail.setTeachers(teachers);
        detail.setMatchInfo(matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId()));

        return detail;
    }


    /** 当学院管理员审核通过后，设置为下一个状态*/
    private void updateWhenAcademyChecked(int matchItemId){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        updateAndSetNextStage(matchItem,stage);
    }

    /** 当前管理员审核通过后，设置为下一个状态*/
    private void updateWhenAdminCheckd(int matchItemId){
        MatchItem matchItem = matchItemService.selectByPrimaryKey(matchItemId);
        Stage stage = stageService.selectByPrimaryKey(matchItem.getNowStageId());
        updateAndSetNextStage(matchItem,stage);
    }
}
