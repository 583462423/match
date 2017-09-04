package com.sduwh.match.service.tmprater.impl;


import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.dao.TmpRaterMapper;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.wrapper.RaterWrapper;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.util.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class TmpRaterServiceImpl implements TmpRaterService {

    @Autowired
    TmpRaterMapper tmpRaterMapper;
    @Autowired
    MatchItemService matchItemService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tmpRaterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TmpRater record) {
        return tmpRaterMapper.insert(record);
    }

    @Override
    public int insertSelective(TmpRater record) {
        return tmpRaterMapper.insertSelective(record);
    }

    @Override
    public TmpRater selectByPrimaryKey(Integer id) {
        return tmpRaterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TmpRater record) {
        return tmpRaterMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TmpRater record) {
        return tmpRaterMapper.updateByPrimaryKey(record);
    }

    /** 获取所有的评委信息*/
    @Override
    public List<RaterWrapper> selectAll() {
        List<TmpRater> tmpRaterss = tmpRaterMapper.selectAll();
        List<RaterWrapper> warppers = tmpRaterss.stream().map(rater->{
            //将来rater转换为RaterWrapper
            RaterWrapper wrapper = new RaterWrapper();
            wrapper.setTmpRater(rater);
            wrapper.setNoDone(matchItemService.selectAllByStringIds(rater.getCommentIds()));
            wrapper.setDone(matchItemService.selectAllByStringIds(rater.getDoneCommentIds()));
            return wrapper;
        }).collect(Collectors.toList());
        return warppers;
    }

    /** 通过ids, startTime,endTime,cnt等创建评委*/
    @Override
    public int createRater(String ids, String startTime, String endTime, Integer cnt) throws ParseException {
        int result = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for(int i=0; i<cnt; i++){
            TmpRater t = new TmpRater();
            t.setCommentIds(ids);
            t.setStartTime(dateFormat.parse(startTime));
            t.setEndTime(dateFormat.parse(endTime));
            t.setLevel(RaterLevel.SCHOOL.getLevel());
            //生成帐号
            t.setUsername(RandomStringUtils.getRandomStringUppercase(String.valueOf(i)));
            t.setPassword(RandomStringUtils.getRandomStringUppercase(6,String.valueOf(i)));
            result += tmpRaterMapper.insert(t);
        }

        return result;
    }
}
