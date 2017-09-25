package com.sduwh.match.service.grade.impl;

import com.sduwh.match.dao.GradeMapper;
import com.sduwh.match.enums.ConcludingStatementStage;
import com.sduwh.match.enums.MatchStage;
import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.jedis.JedisAdapter;
import com.sduwh.match.model.entity.ConcludingStatement;
import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.to.MatchItemTO;
import com.sduwh.match.model.to.ScoreInfo;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import com.sduwh.match.service.grade.GradeService;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.stage.StageService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    MatchItemService matchItemService;
    @Autowired
    StageService stageService;
    @Autowired
    ConcludingStatementService concludingStatementService;
    @Autowired
    GradeService gradeService;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return gradeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Grade record) {
        return gradeMapper.insert(record);
    }

    @Override
    public int insertSelective(Grade record) {
        return gradeMapper.insertSelective(record);
    }

    @Override
    public Grade selectByPrimaryKey(Integer id) {
        return gradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Grade record) {
        return gradeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Grade record) {
        return gradeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Grade> selectByRaterIdAndMatchItemId(int matchItemId, int raterId) {
        return gradeMapper.selectByRaterIdAndMatchItemId(matchItemId,raterId);
    }

    @Override
    public ScoreInfo getScoreInfo(RaterLevel raterLevel, String jedisKey,int matchInfoId,ConcludingStatementStage concludingStatementStage) {
        //每个matchItem id对应的临时评委有几个
        HashMap<Integer,Integer> config = new HashMap<>();
        //获取id对应的评分的总个数
        HashMap<Integer,Float> scores = new HashMap<>();
        //获取id对应的已评分的个数
        HashMap<Integer,Integer> doneScore = new HashMap<>();
        int notDoneCnt[] = new int[1];      //记录未评分的比赛个数
        //获取所有院级的临时评委
        tmpRaterService.selectAll().stream().filter(t->t.getTmpRater().getLevel() == raterLevel.getLevel())
                .filter(r->r.getTmpRater().getMatchInfoId() == matchInfoId)
                .forEach(r->{
                    if(r.getDone() != null){
                        r.getDone().forEach(item->{
                            if(item == null)return;
                            Integer doneCnt = doneScore.get(item.getId());
                            Integer cnt = config.get(item.getId());
                            if(cnt == null) cnt = 0;
                            if(doneCnt == null) doneCnt = 0;

                            //获取rater对应的已审核的比赛的分数
                            List<Grade> grades = selectByRaterIdAndMatchItemId(item.getId(),r.getTmpRater().getId());
                            if(grades != null && grades.size() == 1){
                                Grade tmp = grades.get(0);
                                float tmpScore = tmp.getScore();
                                Float allScore = scores.get(item.getId());
                                if(allScore == null)allScore = 0F;
                                allScore += tmpScore;
                                scores.put(item.getId(),allScore);
                            }
                            doneScore.put(item.getId(),++doneCnt);
                            config.put(item.getId(),++cnt);
                        });
                    }
                    if(r.getNoDone() != null){
                        r.getNoDone().forEach(item->{
                            if(item == null)return;
                            notDoneCnt[0]++;
                            Integer cnt = config.get(item.getId());
                            if(cnt == null){
                                cnt = 0;
                            }
                            config.put(item.getId(),++cnt);
                        });
                    }
                });

        //当前处于评分阶段的比赛
        List<MatchItemTO> matchItemTOS = jedisAdapter.sget(jedisKey).stream()
                .map(Integer::parseInt)
                .map(matchItemService::selectByPrimaryKey)
                .map(item->{
                    int id = item.getId();
                    //将item转换为MatchItemTO
                    MatchItemTO matchItemTO = new MatchItemTO();
                    matchItemTO.setMatchItem(item);
                    if (config.get(id) == null)matchItemTO.setRaterCnt(0);
                    else matchItemTO.setRaterCnt(config.get(id));

                    if(scores.get(id) == null || scores.get(id) == 0){
                        matchItemTO.setAverage(0);
                        matchItemTO.setDoneRater(0);
                    }else{
                        int doneCnt = doneScore.get(id);
                        matchItemTO.setDoneRater(doneCnt);
                        matchItemTO.setAverage(scores.get(id)/doneCnt);
                    }

                    return  matchItemTO;
                })
                .collect(Collectors.toList());
        //获取所有的处于结题检查阶段的比赛，并且处于学院评分或学校阶段前边的比赛的个数
        long allCnt = matchItemService.selectAllByInfoId(matchInfoId).stream().filter(item->{
            Stage stage = stageService.selectByPrimaryKey(item.getNowStageId());
            return stage.getStageFlag() == MatchStage.CONCLUSION_CHECK.getId();
        }).filter(item->{
            //检查是否已经进行评分
            ConcludingStatement concludingStatement = concludingStatementService.selectByMatchItemId(item.getId());
            if(concludingStatement == null)return true;
            if(concludingStatement.getStage() > 0 && concludingStatement.getStage() <= concludingStatementStage.getCode())return true;
            return false;
        }).count();
        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setMatchInfoId(matchInfoId);
        scoreInfo.setCnt(allCnt);
        scoreInfo.setMatchItemTOS(matchItemTOS);
        scoreInfo.setNowCnt(matchItemTOS.size());
        scoreInfo.setLast(allCnt - matchItemTOS.size());
        scoreInfo.setNotDoneCnt(notDoneCnt[0]);
        return scoreInfo;
    }

    @Override
    public List<Grade> selectByMatchItemId(int matchItemId) {
        return gradeMapper.selectByMatchItemId(matchItemId);
    }

    @Override
    public float selectAverangeByMatchItem(int matchItemId) {
        float[] allScore  = new float[1];
        List<Grade> grades = gradeService.selectByMatchItemId(matchItemId);
        if(grades != null || grades.size() != 0){
            grades.forEach(grade->{
                allScore[0] += grade.getScore();
            });
            return allScore[0] / grades.size();
        }return 0;
    }
}
