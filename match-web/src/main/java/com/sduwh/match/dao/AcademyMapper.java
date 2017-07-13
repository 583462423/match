package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Academy;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Academy record);

    int insertSelective(Academy record);

    Academy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Academy record);

    int updateByPrimaryKey(Academy record);
}