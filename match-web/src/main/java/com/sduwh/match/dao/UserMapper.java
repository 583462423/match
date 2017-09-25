package com.sduwh.match.dao;

import com.sduwh.match.enums.Roles;
import com.sduwh.match.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "username,password,status,role_id,join_match_ids,academy_id,specialty_id,last_time,phone,email,name";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where role_id = #{roleId} and academy_id = #{academy}"})
    User selectAcademyUserByAcademyId(@Param("roleId")int roleId,@Param("academy") int academy);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where role_id = 1"})
    User selectSuperUser();
}