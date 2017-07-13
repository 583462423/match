package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Specialty;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Specialty record);

    int insertSelective(Specialty record);

    Specialty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Specialty record);

    int updateByPrimaryKey(Specialty record);
}