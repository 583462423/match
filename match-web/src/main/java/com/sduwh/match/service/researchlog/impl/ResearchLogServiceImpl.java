package com.sduwh.match.service.researchlog.impl;

import com.sduwh.match.dao.ResearchLogMapper;
import com.sduwh.match.model.entity.ResearchLogWithBLOBs;
import com.sduwh.match.service.researchlog.ResearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class ResearchLogServiceImpl implements ResearchLogService {
    @Autowired
    ResearchLogMapper researchLogMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return researchLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ResearchLogWithBLOBs record) {
        return researchLogMapper.insert(record);
    }

    @Override
    public int insertSelective(ResearchLogWithBLOBs record) {
        return researchLogMapper.insertSelective(record);
    }

    @Override
    public ResearchLogWithBLOBs selectByPrimaryKey(Integer id) {
        return researchLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ResearchLogWithBLOBs record) {
        return researchLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ResearchLogWithBLOBs record) {
        return researchLogMapper.updateByPrimaryKey(record);
    }
}
