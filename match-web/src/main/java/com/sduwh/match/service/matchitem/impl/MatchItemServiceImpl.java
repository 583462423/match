package com.sduwh.match.service.matchitem.impl;

import com.sduwh.match.dao.MatchItemMapper;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.MatchStatus;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Pass;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.util.JSONResponseUtils;
import com.sduwh.match.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
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
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
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
        return idList.stream().map(matchItemMapper::selectByPrimaryKey).collect(Collectors.toList());
    }

    @Override
    public List<MatchItem> selectAllByInfoId(Integer id) {
        return matchItemMapper.selectAllByInfoId(id);
    }

    @Override
    public List<MatchItem> selectByStageId(int stageId) {
        return matchItemMapper.selectByStageId(stageId);
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
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(matchItem.getMatchInfoId());
        String[] stages = matchInfo.getAllStage().split(",");
        int nowStage = matchItem.getNowStageId();
        Stage stage = stageService.selectByPrimaryKey(nowStage);
        if(stage.getStageFlag() != MatchStage.GUIDER_VERIFY.getId())throw new RuntimeException("当前状态不是教师状态");

        int nextStage = MatchStatus.ALL_DONE.getValue();
        //获取下一个状态
        for(int i=0; i<stages.length; i++){
            int tmpStage = Integer.parseInt(stages[i]);
            if(nowStage == tmpStage && i!= stages.length-1){
                nextStage = Integer.parseInt(stages[i+1]);
            }
        }

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
            matchItem.setNowStageId(nextStage);
            return updateByPrimaryKey(matchItem);
        }

        return 0;
    }

    @Override
    public String checkDetail(Model model,int id) {
        //通过比赛id来查找比赛信息
        MatchItem matchItem = matchItemService.selectByPrimaryKey(id);
        //TODO 这个地方可以添加一个实体类，该实体类包含所有的比赛信息，
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
    public String checkPass(int matchItemId) {
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

        //完了之后，判断当前审核的老师中是否还有未审核的，如果没有，就设置当前状态为下一状态
        matchItemService.updateWhenTeacherAllChecked(matchItemId);
        return null;
    }
}
