package com.sduwh.match.service.specialty.impl;

import com.sduwh.match.dao.SpecialtyMapper;
import com.sduwh.match.model.entity.Specialty;
import com.sduwh.match.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class SpecialtyServiceImpl implements BaseService<Specialty,Integer> {
    @Autowired
    SpecialtyMapper specialtyMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return specialtyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Specialty record) {
        return specialtyMapper.insert(record);
    }

    @Override
    public int insertSelective(Specialty record) {
        return specialtyMapper.insertSelective(record);
    }

    @Override
    public Specialty selectByPrimaryKey(Integer id) {
        return specialtyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Specialty record) {
        return specialtyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Specialty record) {
        return specialtyMapper.updateByPrimaryKey(record);
    }
}
