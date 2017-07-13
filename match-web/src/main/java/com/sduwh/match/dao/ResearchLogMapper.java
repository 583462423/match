package com.sduwh.match.dao;

import com.sduwh.match.model.entity.ResearchLog;
import com.sduwh.match.model.entity.ResearchLogWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResearchLogWithBLOBs record);

    int insertSelective(ResearchLogWithBLOBs record);

    ResearchLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResearchLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ResearchLogWithBLOBs record);

    int updateByPrimaryKey(ResearchLog record);
}