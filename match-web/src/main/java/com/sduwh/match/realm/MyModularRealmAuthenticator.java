package com.sduwh.match.realm;

import com.sduwh.match.enums.LoginType;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by qxg on 17-9-19.
 * 自定义登陆认证器，根据登陆的类型，来判断调用哪个realm
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    //注入的realm
    private Map<String,Object> myRealms;

    public void setMyRealms(Map<String, Object> myRealms) {
        this.myRealms = myRealms;
    }

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        Realm realm = null;
        //判断当前的authenticationToken是否是UsernamePasswordWithLoginTypeToken,如果不是，说明使用正常的方式调用的login，这个时候使用userRealm进行操作
        if(authenticationToken instanceof UsernamePasswordWithLoginTypeToken){
            UsernamePasswordWithLoginTypeToken token = (UsernamePasswordWithLoginTypeToken) authenticationToken;
            // 登录类型
            LoginType loginType = token.getLoginType();
            //根据loginType不同，调用不同的reaml
            if(loginType == LoginType.USER || loginType == null){
                //默认使用userRealm进行认证
                realm = (Realm) myRealms.get("userRealm");
            }else if(loginType == LoginType.RATER){
                realm = (Realm) myRealms.get("raterRealm");
            }
        }else{
            realm = (Realm) myRealms.get("userRealm");
        }
        return doSingleRealmAuthentication(realm,authenticationToken);
    }
}
