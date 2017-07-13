package com.sduwh.match.realm;

import com.sduwh.match.Enum.UserStatus;
import com.sduwh.match.model.entity.User;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qxg on 17-6-28.
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user  = userService.selectByUsername(token.getUsername());
        System.out.println(user);

        /*if (user == null){
            throw new UnknownAccountException();
        }

        if(user.getStatus().equals(UserStatus.FREEZE.getId())){
            throw new DisabledAccountException();
        }

        if(user.getStatus().equals(UserStatus.NOT_ACTIVE.getId())){
            //TODO 抛出未激活异常
        }
        */
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //System.out.println("doGetAuthorizationInfo被调用！！！");
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.selectByUsername(username);
        String role = user.getRole().getName();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(role);
        return info;
    }
}
