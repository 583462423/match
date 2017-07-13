package com.sduwh.match.service.permission;

import com.sduwh.match.model.entity.Permission;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface PermissionService extends BaseService<Permission,Integer> {
    List<Permission> selectAll();
}
