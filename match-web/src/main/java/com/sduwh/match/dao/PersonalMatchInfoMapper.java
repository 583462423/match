package com.sduwh.match.dao;

import com.sduwh.match.model.entity.PersonalMatchInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonalMatchInfoMapper {
    String TABLE_NAME = "personal_match_info";
    String INSERT_FIELDS = "user_id,match_item_id,type,status,job_assignment";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;
    int deleteByPrimaryKey(Integer id);

    int insert(PersonalMatchInfo record);

    int insertSelective(PersonalMatchInfo record);

    PersonalMatchInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PersonalMatchInfo record);

    int updateByPrimaryKey(PersonalMatchInfo record);

    @Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,"where user_id = #{userId}"})
    List<PersonalMatchInfo> selectByUserId(@Param("userId") int userId);

    @Delete({"delete from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    int deleteAllByMatchItemId(@Param("matchItemId") int matchItemId);
}