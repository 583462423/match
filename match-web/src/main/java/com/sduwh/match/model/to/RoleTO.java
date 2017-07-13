package com.sduwh.match.model.to;

import com.sduwh.match.model.entity.Permission;
import com.sduwh.match.model.entity.Role;

import java.util.List;

/**
 * Created by qxg on 17-7-7.
 */
public class RoleTO {
    private Integer id;
    private String name;
    private String des;
    private List<Permission> permissions;
    private String ps;                    //permissions的string集合形式的字符串

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPs() {
        return ps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "RoleTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
