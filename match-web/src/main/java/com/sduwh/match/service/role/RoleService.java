package com.sduwh.match.service.role;

import com.sduwh.match.model.entity.Role;
import com.sduwh.match.model.to.RoleTO;
import com.sduwh.match.service.BaseService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface RoleService extends BaseService<Role,Integer> {
    List<RoleTO> selectAllRoleTO();
    List<Role> selectAll();
    Role selectByRoleName(String name);
}
