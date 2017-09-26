package com.sduwh.match.service.user.impl;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.UserStatus;
import com.sduwh.match.dao.AcademyMapper;
import com.sduwh.match.dao.MatchItemMapper;
import com.sduwh.match.dao.SpecialtyMapper;
import com.sduwh.match.dao.UserMapper;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.user.UserService;
import com.sduwh.match.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MatchItemMapper matchItemMapper;
    @Autowired
    AcademyMapper academyMapper;
    @Autowired
    SpecialtyMapper specialtyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public UserWrapper selectUserWrapperByUser(User user) {
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setUser(user);
        userWrapper.setStatus(UserStatus.NORMAL.getDes(user.getStatus()));
        String joinMatchIds = user.getJoinMatchIds();
        List<MatchItem> matchItems = null;
        if(!StringUtils.nullOrEmpty(joinMatchIds)){
            userWrapper.setJoinMatch(Arrays.stream(joinMatchIds.split(","))
                    .map(e->matchItemMapper.selectByPrimaryKey(Integer.valueOf(e)))
                    .collect(Collectors.toList()));;
        }else{
            userWrapper.setJoinMatch(null);
        }

        userWrapper.setAcademy(academyMapper.selectByPrimaryKey(user.getAcademyId()));
        userWrapper.setSpecialty(specialtyMapper.selectByPrimaryKey(user.getSpecialtyId()));
        System.out.println(userWrapper);
        return userWrapper;
    }

    @Override
    public String checkStatus(User user) {
        if(user.getStatus() == UserStatus.FREEZE.getId()){
            return BaseController.UNAUTH;
        }
        if(user.getStatus() == UserStatus.NOT_ACTIVE.getId()){
            return BaseController.TO_ACTIVE;
        }
        return null;
    }

    @Override
    public User selectAcademyUserByAcademyId(int roleId,int academyId) {

        return userMapper.selectAcademyUserByAcademyId(roleId,academyId);
    }

    @Override
    public User selectSuperUser() {
        return userMapper.selectSuperUser();
    }

    @Override
    public List<User> selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }


}
