package com.sduwh.match.dao;

import com.sduwh.match.model.entity.Pass;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qxg on 17-9-1.
 * 以后这里会增加不少字段，比如什么时候审核的balabala,先不管这个
 */
@Mapper
@Repository
public interface PassDAO {
    String TABLE_NAME = "pass";
    String INSERT_FIELDS = "user_id,match_item_id,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id = #{userId} " +
            "and match_item_id = #{matchItemId}"})
    Pass selectByUserAndItem(@Param("userId")int userId,@Param("matchItemId") int matchItemId);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id = #{userId}"})
    List<Pass> selectByUserId(@Param("userId")int userId);


    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{userId},#{matchItemId},#{status})"})
    int insert(Pass pass);

    @Delete({"delete from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    int deleteAllByMatchItemId(@Param("matchItemId") int matchItemId);
}
