package com.sduwh.match.service.personalmatchinfo.impl;

import com.sduwh.match.dao.PersonalMatchInfoMapper;
import com.sduwh.match.model.entity.PersonalMatchInfo;
import com.sduwh.match.service.BaseService;
import com.sduwh.match.service.personalmatchinfo.PersonalMatchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class PersonalMatchInfoServiceImpl implements PersonalMatchInfoService {

    @Autowired
    PersonalMatchInfoMapper personalMatchInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return personalMatchInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PersonalMatchInfo record) {
        return personalMatchInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(PersonalMatchInfo record) {
        return personalMatchInfoMapper.insertSelective(record);
    }

    @Override
    public PersonalMatchInfo selectByPrimaryKey(Integer id) {
        return personalMatchInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PersonalMatchInfo record) {
        return personalMatchInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PersonalMatchInfo record) {
        return personalMatchInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<PersonalMatchInfo> selectByUserId(int id) {
        return personalMatchInfoMapper.selectByUserId(id);
    }

    @Override
    public int deleteAllByMatchItemId(int matchItemId) {
        return personalMatchInfoMapper.deleteAllByMatchItemId(matchItemId);
    }
}
