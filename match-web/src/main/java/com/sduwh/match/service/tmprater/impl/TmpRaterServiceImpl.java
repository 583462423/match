package com.sduwh.match.service.tmprater.impl;


import com.sduwh.match.dao.TmpRaterMapper;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.wrapper.RaterWrapper;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.matchtype.MatchTypeService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
