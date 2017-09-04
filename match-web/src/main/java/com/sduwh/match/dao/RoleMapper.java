package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    String TABLE_NAME = "role";
    String SELECT_FILEDS = "id, name, permission_ids,des";
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll();

    @Select({"select ",SELECT_FILEDS,"from" ,TABLE_NAME,"where name = #{name}"})
    Role selectByRoleName(@Param("name") String name);
}