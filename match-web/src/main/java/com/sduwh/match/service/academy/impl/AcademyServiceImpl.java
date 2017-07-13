package com.sduwh.match.service.academy.impl;

import com.sduwh.match.dao.AcademyMapper;
import com.sduwh.match.model.entity.Academy;
import com.sduwh.match.service.academy.AcademyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class AcademyServiceImpl implements AcademyService {

    @Autowired
    AcademyMapper academyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return academyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Academy record) {
        return academyMapper.insert(record);
    }

    @Override
    public int insertSelective(Academy record) {
        return academyMapper.insertSelective(record);
    }

    @Override
    public Academy selectByPrimaryKey(Integer id) {
        return academyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Academy record) {
        return academyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Academy record) {
        return academyMapper.updateByPrimaryKey(record);
    }
}
