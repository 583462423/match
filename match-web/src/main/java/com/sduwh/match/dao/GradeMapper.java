package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeMapper {
    String TABLE_NAME = "grade";
    String INSERT_FIELDS = "score,comment,level,match_item_id,rater_id";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);


    int updateByPrimaryKey(Grade record);

    /** 使用list的原因是担心数据库问题导致查出来的数据是多条*/
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{matchItemId} and rater_id = #{raterId}"})
    List<Grade> selectByRaterIdAndMatchItemId(@Param("matchItemId")int matchItemId, @Param("raterId")int raterId);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    List<Grade> selectByMatchItemId(@Param("matchItemId")int matchItemId);
}