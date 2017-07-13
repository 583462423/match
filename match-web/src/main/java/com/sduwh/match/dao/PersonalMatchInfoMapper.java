package com.sduwh.match.dao;

import com.sduwh.match.model.entity.PersonalMatchInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalMatchInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PersonalMatchInfo record);

    int insertSelective(PersonalMatchInfo record);

    PersonalMatchInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PersonalMatchInfo record);

    int updateByPrimaryKey(PersonalMatchInfo record);
}