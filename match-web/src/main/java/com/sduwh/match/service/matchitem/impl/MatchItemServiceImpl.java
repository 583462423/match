package com.sduwh.match.service.matchitem.impl;

import com.sduwh.match.dao.MatchItemMapper;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.MatchStatus;
import com.sduwh.match.enums.PassStatus;
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
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
            return matchItemMapper.selectByPrimaryKey(integer);
        }
        for(int i=0; i<stageIds.length; i++){
            int tmpStage = Integer.parseInt(stageIds[i]);
            if(stageId == tmpStage && i!= stageIds.length-1){
                nextStageId = Integer.parseInt(stageIds[i+1]);
            }
        }
        //判断当前的stageId是否已经超时,stageId可能是-1,所以stage可能为空
        Stage stage = stageService.selectByPrimaryKey(stageId);

        if (stage!=null && stageService.checkEnd(stage)){
            //当前状态已过期，那么就需要重新设置状态,即更新操作
            //当前状态需要根据实际情况进行判断，比如当前状态为审核，需要取得审核表，如果没有，说明为通过，直接将状态设置为-2.
            nextStageId = getNextStage(stage,matchItem,nextStageId);
            matchItem.setNowStageId(nextStageId);
            updateByPrimaryKey(matchItem);
            //更新完毕，重新调用该方法,目的就是防止下一个阶段依然是无效期
            return selectByPrimaryKey(integer);
        }else{
            return matchItemMapper.selectByPrimaryKey(integer);
        }
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



    /** 根据当前状态，获取比赛的下一个状态是什么，注意调用这个方法的前提是，当前的状态已过期*/
    private int getNextStage(Stage stage,MatchItem matchItem,int nextStage){

        //有时候不是我不用swtich而是这个地方的逻辑不允许我使用switch，so,如果有人在维护代码的时候看到这个地方的代码，请轻喷，否则，大爷您来完成他！
        if(stage.getStageFlag() == MatchStage.GUIDER_VERIFY.getId()){
            //如果当前的状态是指导老师审核，那么就查看matchItem的指导老师是谁，如果指导老师们都审核了，那么下一个状态就确实应该是下一个状态= =
            if(Arrays.stream(matchItem.getTeacherIds().split(","))
                    .map(Integer::parseInt)
                    .allMatch(e->{
                        Pass pass = passService.selectByUserAndItem(e,matchItem.getId());
                        return passService.checkPass(pass);
                    })
                    ){
                //如果所有老师都审核通过了
                return nextStage;
            }else{
                return MatchStatus.TEACHER_NOT_PASS.getValue();
            }
        }

        //如果没有符合条件的，就下一个状态吧
        return nextStage;

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
