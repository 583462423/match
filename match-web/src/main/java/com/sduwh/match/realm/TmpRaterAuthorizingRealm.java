package com.sduwh.match.realm;

import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qxg on 17-9-19.
 */
public class TmpRaterAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    TmpRaterService tmpRaterService;

    /** 获取角色*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /** 判断用户名密码等的*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        TmpRater rater = tmpRaterService.selectByUsername(username);
        if (rater == null){
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(rater.getUsername(),rater.getPassword(),getName());
    }
}
