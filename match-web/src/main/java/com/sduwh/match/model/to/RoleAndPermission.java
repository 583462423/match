package com.sduwh.match.model.to;

import com.sduwh.match.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public class RoleAndPermission {
    private Role role;
    private List<String> permissions;

    public RoleAndPermission(){
        permissions = new ArrayList<>();
    }

    public RoleAndPermission(Role role,List<String> permissions){
        this.role = role;
        this.permissions = permissions;
    }

    public void addPermission(String permission){
        permissions.add(permission);
    }

    public void addAllPermission(List<String> ps){
        permissions.addAll(ps);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
