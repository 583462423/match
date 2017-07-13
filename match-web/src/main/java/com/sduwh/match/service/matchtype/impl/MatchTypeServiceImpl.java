package com.sduwh.match.service.matchtype.impl;

import com.sduwh.match.dao.MatchType2Mapper;
import com.sduwh.match.dao.MatchTypeMapper;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.service.matchtype.MatchTypeService;
import com.sun.deploy.security.ValidationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-30.
 */
@Service
public class MatchTypeServiceImpl implements MatchTypeService {

    @Autowired
    MatchTypeMapper matchTypeMapper;
    @Autowired
    MatchType2Mapper matchType2Mapper;

    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return matchTypeMapper.deleteByPrimaryKey(integer);
    }

    @Override
    public int insert(MatchType record) {
        return matchTypeMapper.insert(record);
    }

    @Override
    public int insertSelective(MatchType record) {
        return matchTypeMapper.insertSelective(record);
    }

    @Override
    public MatchType selectByPrimaryKey(Integer integer) {
        return matchTypeMapper.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(MatchType record) {
        return matchTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchType record) {
        return 0;
    }

    @Override
    public List<MatchType> selectAll() {
        return matchTypeMapper.selectAll();
    }

    @Override
    public List<MatchTypeWrapper> selectAllWrapper() {
        return matchTypeMapper.selectAll()
                .stream()
                .map(e->{
                    MatchTypeWrapper wrapper = new MatchTypeWrapper();
                    wrapper.setMatchType(e);
                    wrapper.setMatchType2s(matchType2Mapper.selectByType1Id(e.getId()));
                    return wrapper;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MatchType selectByName(String name) {
        return matchTypeMapper.selectByName(name);
    }
}
