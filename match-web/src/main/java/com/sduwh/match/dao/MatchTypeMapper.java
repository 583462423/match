package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MatchType record);

    int insertSelective(MatchType record);

    MatchType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MatchType record);

    int updateByPrimaryKey(MatchType record);

    List<MatchType> selectAll();

    MatchType selectByName(String name);
}