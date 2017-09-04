package com.sduwh.match.service.user;

import com.sduwh.match.model.entity.User;
import com.sduwh.match.model.wrapper.UserWrapper;
import com.sduwh.match.service.BaseService;

/**
 * Created by qxg on 17-6-29.
 */


public interface UserService extends BaseService<User,Integer>{
    public User selectByUsername(String username);
    public UserWrapper selectUserWrapperByUser(User user);
    /** 判断当前用户的状态，是已激活还是未激活啥的*/
    public String checkStatus(User user);
}
