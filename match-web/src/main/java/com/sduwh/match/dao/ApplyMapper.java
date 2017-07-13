package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Apply;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);
}