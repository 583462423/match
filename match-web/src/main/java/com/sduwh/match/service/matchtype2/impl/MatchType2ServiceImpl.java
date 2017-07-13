package com.sduwh.match.service.matchtype2.impl;

import com.sduwh.match.dao.MatchType2Mapper;
import com.sduwh.match.dao.MatchTypeMapper;
import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.model.wrapper.MatchType2Wrapper;
import com.sduwh.match.service.matchtype2.MatchType2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-30.
 */
@Service
public class MatchType2ServiceImpl implements MatchType2Service {

    @Autowired
    MatchType2Mapper matchType2Mapper;
    @Autowired
    MatchTypeMapper matchTypeMapper;

    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return matchType2Mapper.deleteByPrimaryKey(integer);
    }

    @Override
    public int insert(MatchType2 record) {
        return matchType2Mapper.insert(record);
    }

    @Override
    public int insertSelective(MatchType2 record) {
        return matchType2Mapper.insertSelective(record);
    }

    @Override
    public MatchType2 selectByPrimaryKey(Integer integer) {
        return matchType2Mapper.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(MatchType2 record) {
        return matchType2Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchType2 record) {
        return 0;
    }

    @Override
    public List<MatchType2> selectByType1Id(Integer id) {
        return matchType2Mapper.selectByType1Id(id);
    }

    @Override
    public List<MatchType2> selectByAll() {
        return matchType2Mapper.selectAll();
    }

    public List<MatchType2Wrapper> selectALlType2Wrapper(){
        return selectByAll().stream()
                .map(e->{
                    MatchType2Wrapper wrapper = new MatchType2Wrapper();
                    wrapper.setMatchType2(e);
                    wrapper.setMatchType(matchTypeMapper.selectByPrimaryKey(e.getMatchTypeId()));
                    return wrapper;
                }).collect(Collectors.toList());
    }
}
