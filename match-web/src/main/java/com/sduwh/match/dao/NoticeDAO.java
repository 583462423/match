package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Notice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {
    String TABLE_NAME = "notice";
    String INSERT_FIELDS = "title,content,level,start_time,end_time,is_valid";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{title},#{content},#{level},#{startTime},#{endTime},#{isValid})"})
    int insert(Notice notice);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME})
    List<Notice> selectAll();

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where is_valid = 1 and level = #{level} order by id desc"})
    List<Notice> selectByLevelAndValid(@Param("level")int level);
}
