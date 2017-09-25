package com.sduwh.match.realm;

import com.sduwh.match.enums.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by qxg on 17-9-19.
 */
public class UsernamePasswordWithLoginTypeToken extends UsernamePasswordToken {
    private LoginType loginType;

    public UsernamePasswordWithLoginTypeToken(final String username,final String password,LoginType loginType){
        super(username,password);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
