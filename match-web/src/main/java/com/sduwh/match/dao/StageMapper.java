package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Stage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StageMapper {
    String TABLE_NAME = "stage";
    String INSERT_FIELDS = "name,type,start_time,end_time,stage_flag";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;


    int deleteByPrimaryKey(Integer id);

    int insert(Stage record);

    int insertSelective(Stage record);

    Stage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stage record);

    int updateByPrimaryKey(Stage record);

    List<Stage> selectByIdList(List<Integer> ids);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where stage_flag = #{flag}"})
    List<Stage> selectByStageFlag(@Param("flag")int flag);
}