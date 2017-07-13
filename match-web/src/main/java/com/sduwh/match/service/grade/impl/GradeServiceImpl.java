package com.sduwh.match.service.grade.impl;

import com.sduwh.match.dao.GradeMapper;
import com.sduwh.match.model.entity.Grade;
import com.sduwh.match.service.grade.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeMapper gradeMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return gradeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Grade record) {
        return gradeMapper.insert(record);
    }

    @Override
    public int insertSelective(Grade record) {
        return gradeMapper.insertSelective(record);
    }

    @Override
    public Grade selectByPrimaryKey(Integer id) {
        return gradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Grade record) {
        return gradeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Grade record) {
        return gradeMapper.updateByPrimaryKey(record);
    }
}
