package com.sduwh.match.service.user;

import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */


public interface UserService extends BaseService<User,Integer>{
    public User selectByUsername(String username);
    public UserWrapper selectUserWrapperByUser(User user);
    /** 判断当前用户的状态，是已激活还是未激活啥的*/
    public String checkStatus(User user);

    /** 限定学院管理员只有一个，所以查询学院管理员返回只有一个*/
    public User selectAcademyUserByAcademyId(int roleId,int academyId);

    /** 获取学校的超级管理员，该管理员只有一个*/
    public User selectSuperUser();

    /** 通过邮箱来获取对应的用户名，使用List防止出现BUG*/
    public List<User> selectByEmail(String email);
}
