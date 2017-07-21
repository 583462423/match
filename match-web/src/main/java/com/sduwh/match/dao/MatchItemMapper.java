package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MatchItem record);

    int insertSelective(MatchItem record);

    MatchItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MatchItem record);

    int updateByPrimaryKey(MatchItem record);

    List<MatchItem> selectAllByInfoId(Integer id);

}