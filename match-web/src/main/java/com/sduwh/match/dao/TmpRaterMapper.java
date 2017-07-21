package com.sduwh.match.dao;

import com.sduwh.match.model.entity.TmpRater;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TmpRaterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TmpRater record);

    int insertSelective(TmpRater record);



    TmpRater selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TmpRater record);

    int updateByPrimaryKey(TmpRater record);

    List<TmpRater> selectAll();
}