package com.sduwh.match.service.role.impl;

import com.sduwh.match.dao.PermissionMapper;
import com.sduwh.match.dao.RoleMapper;
import com.sduwh.match.model.entity.Permission;
import com.sduwh.match.model.entity.Role;
import com.sduwh.match.model.to.RoleTO;
import com.sduwh.match.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return roleMapper.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<RoleTO> selectAllRoleTO() {
        return selectAll()
                .stream()
                .map(e->{
                    String permissionIds = e.getPermissionIds();
                    RoleTO roleTO = new RoleTO();
                    roleTO.setId(e.getId());
                    roleTO.setName(e.getName());
                    roleTO.setDes(e.getDes());
                    if (permissionIds == null || permissionIds.equals(""))roleTO.setPermissions(null);
                    else
                        roleTO.setPermissions(
                            permissionMapper.selectAllByListID(
                                    Arrays.asList(permissionIds.split(","))
                                                    .stream()
                                                    .map(Integer::parseInt).
                                                    collect(Collectors.toList())
                        )
                    );
                    List<Permission> permissions = roleTO.getPermissions();
                    if (permissionIds == null || permissionIds == "")roleTO.setPs("");
                    else
                        roleTO.setPs(roleTO.getPermissions().stream()
                            .map(p->String.valueOf(p.getId()))
                            .collect(Collectors.joining(" ")));
                    return roleTO;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

}
