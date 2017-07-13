package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchType2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchType2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MatchType2 record);

    int insertSelective(MatchType2 record);

    MatchType2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MatchType2 record);

    int updateByPrimaryKey(MatchType2 record);

    List<MatchType2> selectByType1Id(Integer id);

    List<MatchType2> selectAll();
}