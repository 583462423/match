package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MatchItemMapper {

    String TABLE_NAME = "match_item";
    String INSERT_FIELDS = "title,member_ids,apply_id,teacher_ids,now_stage_id,next_stage_id,academy_id,match_info_id,leader_ids,is_supply,by_time";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    int insert(MatchItem record);

    int insertSelective(MatchItem record);

    MatchItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MatchItem record);

    int updateByPrimaryKey(MatchItem record);

    List<MatchItem> selectAllByInfoId(Integer id);

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME,"where now_stage_id = #{stageId}"})
    List<MatchItem> selectByStageId(@Param("stageId") int stageId);
}