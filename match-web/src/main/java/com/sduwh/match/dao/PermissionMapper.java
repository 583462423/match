package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    Permission selectByRoleId(Integer id);

    List<Permission> selectAll();

    List<Permission> selectAllByListID(List<Integer> ids);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}