package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.Permission;
import com.sduwh.match.model.to.RoleTO;

import java.util.List;

/**
 * Created by qxg on 17-7-10.
 * 因为前端方面，需要所有的permission,并且也需要RoleTO的转型对象，所以使用包装将两者包装起来
 */
public class RoleTOWrapper {
    private List<RoleTO> roleTOS;
    private List<Permission> allPermissions;
    private String ps;  //为了前端而设置的一个所有permission的字符串

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPs() {
        return ps;
    }

    public List<RoleTO> getRoleTOS() {
        return roleTOS;
    }

    public void setRoleTOS(List<RoleTO> roleTOS) {
        this.roleTOS = roleTOS;
    }

    public List<Permission> getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(List<Permission> allPermissions) {
        this.allPermissions = allPermissions;
    }
}

