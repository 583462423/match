package com.sduwh.match.controller.admin;

import com.sduwh.match.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.model.entity.Permission;
import com.sduwh.match.model.entity.Role;
import com.sduwh.match.model.to.RoleTO;
import com.sduwh.match.model.wrapper.RoleTOWrapper;
import com.sduwh.match.service.permission.PermissionService;
import com.sduwh.match.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-7-6.
 * 权限管理
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
    /** 目前的想法是进行细粒度的控制，对于访问的每个页面，都有相应的权限，而5个角色是固定的，不用编写查看删除等页面
     *  但是对于权限控制来说，需要编写5个角色的所有权限，并能进行选中，操作，即只有选中等操作。
     * */

    private static final String PERMISSIONS = "/admin/permission";

    //TODO 需要更改role的字段和permission的字段，如果是上述需求，需要将permission_ids加入到role中，而permission中的信息是固定的
    //TODO 如果要更改，还要同时更改mapper,service，以及model中的信息，任务比较重啊！
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /** 获取所有的角色和角色权限*/
    @GetMapping("/all")
    public String getPermissions(Map<String,RoleTOWrapper> map){
        RoleTOWrapper wrapper = new RoleTOWrapper();
        wrapper.setRoleTOS(roleService.selectAllRoleTO());
        System.out.println(wrapper.getRoleTOS().stream().peek(to-> System.out.println(to.getPs())));
        List<Permission> permissions = permissionService.selectAll();
        wrapper.setAllPermissions(permissions);
        wrapper.setPs(permissions.stream()
                .map(p->String.valueOf(p.getId()))
                .collect(Collectors.joining(" ")));
        System.out.println(wrapper.getPs());

        map.put("wrapper",wrapper);
        return PERMISSIONS;
    }

    /** 修改角色的权限*/
    @PostMapping("/alter")
    @ResponseBody
    public ResponseResult alterRolePermission(@RequestParam("id") Integer id,@RequestParam("permissionIds[]") String[] permissionIds) {
        Role role = new Role();
        role.setId(id);
        if (permissionIds.length == 0)role.setPermissionIds("");
        else
            role.setPermissionIds(Arrays.stream(permissionIds).collect(Collectors.joining(",")));
        if(roleService.updateByPrimaryKeySelective(role) == 1)return success(true);
        return fail(false,"未知错误");
    }
}
