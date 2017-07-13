package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MatchInfo record);

    int insertSelective(MatchInfo record);

    MatchInfo selectByPrimaryKey(Integer id);

    List<MatchInfo> selectAll();

    int updateByPrimaryKeySelective(MatchInfo record);

    int updateByPrimaryKey(MatchInfo record);
}