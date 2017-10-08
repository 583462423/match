package com.sduwh.match.service.tmprater;

import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.wrapper.RaterWrapper;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */

public interface TmpRaterService extends BaseService<TmpRater,Integer> {
    List<RaterWrapper> selectAll();
    List<TmpRater> createRater(String ids,String startTime,String endTime,Integer cnt,int level,int matchInfoId) throws ParseException;
    TmpRater selectByUsername(String username);
    /** 判断当前rater是否已经超过了最后允许的登陆时期*/
    public boolean checkEnd(TmpRater rater);
    /** 判断当前rater是否已经达到开始登陆时期*/
    public boolean checkStart(TmpRater rater);

    /** 获取所有对应的级别的评委表，并计算出分数信息，评委已评论信息*/
    
}
