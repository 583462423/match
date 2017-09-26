package com.sduwh.match.service.researchlog.impl;

import com.sduwh.match.dao.ResearchLogMapper;
import com.sduwh.match.model.entity.ResearchLog;
import com.sduwh.match.service.researchlog.ResearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int insert(ResearchLog record) {
        return researchLogMapper.insert(record);
    }

    @Override
    public int insertSelective(ResearchLog record) {
        return researchLogMapper.insertSelective(record);
    }

    @Override
    public ResearchLog selectByPrimaryKey(Integer id) {
        return researchLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ResearchLog record) {
        return researchLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ResearchLog record) {
        return researchLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ResearchLog> getAllLogByMatchItemId(int matchItemId) {
        return researchLogMapper.getAllLogByMatchItemId(matchItemId);
    }

    @Override
    public int deleteAllByMatchItemId(int matchItemId) {
        return researchLogMapper.deleteAllByMatchItemId(matchItemId);
    }
}
