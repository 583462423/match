package com.sduwh.match.dao;

import com.sduwh.match.model.entity.ResearchLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface ResearchLogMapper {
    String TABLE_NAME = "research_log";
    String INSERT_FIELDS = "title,content,comment,is_public,times,last_time,match_item_id,start_time,end_time,time";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{title},#{content},#{comment},#{isPublic},#{times},#{lastTime},#{matchItemId},#{startTime},#{endTime},#{time})"})
    int insert(ResearchLog record);

    int insertSelective(ResearchLog record);

    /** 常用的先写上，其他后续写*/
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    ResearchLog selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(ResearchLog record);

    int updateByPrimaryKeyWithBLOBs(ResearchLog record);

    int updateByPrimaryKey(ResearchLog record);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    List<ResearchLog> getAllLogByMatchItemId(@Param("matchItemId") int matchItemId);
}