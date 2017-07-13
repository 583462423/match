package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Stage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stage record);

    int insertSelective(Stage record);

    Stage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stage record);

    int updateByPrimaryKey(Stage record);

    List<Stage> selectByIdList(List<Integer> ids);
}