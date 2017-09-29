package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MatchInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchInfoMapper {
    String TABLE_NAME = "match_info";
    String INSERT_FIELDS = "name,type1,type2,level,all_stage,leader_num,member_num,teacher_num,leader_in_num,member_in_num,teacher_in_num,create_time,start_time,end_time,supply";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int deleteByPrimaryKey(Integer id);

    int insert(MatchInfo record);

    int insertSelective(MatchInfo record);

    MatchInfo selectByPrimaryKey(Integer id);

    List<MatchInfo> selectAll();

    int updateByPrimaryKeySelective(MatchInfo record);

    int updateByPrimaryKey(MatchInfo record);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name = #{name}"})
    List<MatchInfo> selectByName(@Param("name")String name);
}