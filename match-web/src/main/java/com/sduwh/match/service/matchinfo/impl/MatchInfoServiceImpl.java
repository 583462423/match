package com.sduwh.match.service.matchinfo.impl;

import com.sduwh.match.dao.MatchInfoMapper;
import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.model.entity.MatchInfo;

import com.sduwh.match.model.entity.PersonalMatchInfo;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.to.MatchInfoTO;
import com.sduwh.match.model.wrapper.MatchInfoWrapper;
import com.sduwh.match.service.apply.ApplyService;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.middlecheck.MiddleCheckService;
import com.sduwh.match.service.pass.PassService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import com.sduwh.match.service.researchlog.ResearchLogService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.transfermember.TransferMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class MatchInfoServiceImpl implements MatchInfoService {

    @Autowired
    MatchInfoMapper matchInfoMapper;
    @Autowired
    StageService stageService;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    ApplyService applyService;
    @Autowired
    GradeService gradeService;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    MiddleCheckService middleCheckService;
    @Autowired
    PassService passService;
    @Autowired
    PersonalMatchInfoService personalMatchInfoService;
    @Autowired
    ResearchLogService researchLogService;
    @Autowired
    TransferMemberService transferMemberService;

    private static final int UPDATE = 1;
    private static final int INSERT = 0;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return matchInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MatchInfo record) {
        return matchInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MatchInfo record) {
        return matchInfoMapper.insertSelective(record);
    }

    @Override
    public MatchInfo selectByPrimaryKey(Integer id) {
        return matchInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MatchInfo record) {
        return matchInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchInfo record) {
        return matchInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatchInfo> selectAll() {
        return matchInfoMapper.selectAll();
    }

    /**
     * 虽然说是通过TO来创建一个数据，但是首先要把这个对象转换为MatchInfo对象
     */
    @Override
    public int createMatchInfoByTO(MatchInfoTO to) {
        return matchInfoMapper.insert(TO2MatchInfo(to,INSERT));
    }

    @Override
    public int updateMatchInfoByTO(MatchInfoTO to) {
        return matchInfoMapper.updateByPrimaryKey(TO2MatchInfo(to,UPDATE));
    }

    @Override
    public boolean checkEnd(MatchInfo info) {
        return info.getEndTime().before(new Date());
    }

    @Override
    public boolean checkIsRunning(MatchInfo matchInfo) {
        Date now = new Date();
        return matchInfo.getStartTime().before(now) && matchInfo.getEndTime().after(now);
    }

    @Override
    @Transactional
    public boolean deleteAllOtherByMatchInfoId(int id) {
        matchItemService.selectAllByInfoId(id).stream().forEach(e->{
            //删除每个比赛对应的申请表
            applyService.deleteByPrimaryKey(e.getApplyId());
            //删除对应的评分表
            gradeService.deleteAllByMatchItemId(e.getId());
            //删除对应的结题答辩表
            concludingStatementService.deleteAllByMatchItemId(e.getId());
            //删除对应的中期答辩表
            middleCheckService.deleteAllByMatchItemId(e.getId());
            //删除pass表
            passService.deleteAllByMatchItemId(e.getId());
            //删除个人比赛表
            personalMatchInfoService.deleteAllByMatchItemId(e.getId());
            //删除日志表
            researchLogService.deleteAllByMatchItemId(e.getId());
            //删除成员转换表
            transferMemberService.deleteAllByMatchItemId(e.getId());
            //最后删除比赛
            matchItemService.deleteByPrimaryKey(e.getId());
        });

        //删除matchInfo,但是首先要获取所有的matchinfo的stage
        MatchInfo matchInfo = selectByPrimaryKey(id);
        Arrays.stream(matchInfo.getAllStage().split(",")).forEach(e->{
            stageService.deleteByPrimaryKey(Integer.parseInt(e));
        });
        deleteByPrimaryKey(matchInfo.getId());
        return true;
    }

    /** 将MatchInfoTO转换为MatchInfo*/
    public MatchInfo TO2MatchInfo(MatchInfoTO to,int flag){
        MatchInfo info = new MatchInfo();
        info.setId(to.getId());
        info.setCreateTime(new Timestamp(System.currentTimeMillis()));
        info.setLeaderInNum(to.getLeaderInNum());
        info.setLeaderNum(to.getLeaderNum());
        info.setLevel(to.getLevel());
        info.setMemberInNum(to.getMemberInNum());
        info.setMemberNum(to.getMemberNum());
        info.setName(to.getName());
        info.setTeacherInNum(to.getTeacherInNum());
        info.setTeacherNum(to.getTeacherNum());
        info.setType1(to.getType1());
        info.setType2(to.getType2());
        info.setStartTime(to.getInfoStartTime());
        info.setEndTime(to.getInfoEndTime());
        info.setSupply(to.getSupply());

        List<Stage> stages = new ArrayList<>();
        List<Integer> isChoose = to.getIsChoose();
        List<Timestamp> startTime = to.getStartTime();
        List<Timestamp> endTime = to.getEndTime();
        List<String> toGetStages = to.getStageName();

        if(flag == INSERT){
            for(int i=0; i<isChoose.size(); i++){
                if (isChoose.get(i)!=0){
                    //说明有选择
                    Stage stage = new Stage();
                    stage.setEndTime(endTime.get(i));
                    stage.setStartTime(startTime.get(i));
                    stage.setStageFlag(isChoose.get(i));
                    stage.setName(toGetStages.get(i));
                    //这个地方就需要插入
                    stageService.insert(stage);
                    //这个是插入id  stage.getId();
                    stages.add(stage);
                }
            }
        }else if(flag == UPDATE){
            //如果是更新，则应该调用方法进行更新
            for(int i=0; i<isChoose.size(); i++){
                Stage stage = new Stage();
                stage.setId(isChoose.get(i));
                stage.setEndTime(endTime.get(i));
                stage.setStartTime(startTime.get(i));
                //stage.setStageFlag(isChoose.get(i)); 这条语句导致严重bug出现～～～～！！！！
                stage.setName(toGetStages.get(i));
                //这个地方就需要插入
                stageService.updateByPrimaryKeySelective(stage);
                //这个是插入id  stage.getId();
                stages.add(stage);
            }
        }
        info.setAllStage(stages.stream().map(e->e.getId().toString()).collect(Collectors.joining(",")));
        //System.out.println(info);
        return info;
    }

    public static void main(String[] args){
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
