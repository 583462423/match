package com.sduwh.match.dao;

import com.sduwh.match.model.entity.TmpRater;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TmpRaterMapper {

    String TABLE_NAME = "tmp_rater";
    String INSERT_FIELDS = "username,password,comment_ids,done_comment_ids,level,start_time,end_time,match_info_id";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    int insert(TmpRater record);

    int insertSelective(TmpRater record);

    TmpRater selectByPrimaryKey(Integer id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where username = #{username}"})
    TmpRater selectByUsername(@Param("username") String username);

    int updateByPrimaryKeySelective(TmpRater record);

    int updateByPrimaryKey(TmpRater record);

    List<TmpRater> selectAll();
}