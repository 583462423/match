package com.sduwh.match.service.user;

import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.BaseService;

/**
 * Created by qxg on 17-6-29.
 */


public interface UserService extends BaseService<User,Integer>{
    public User selectByUsername(String username);
}
