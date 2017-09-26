package com.sduwh.match.dao;

import com.sduwh.match.model.entity.TransferMember;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qxg on 17-9-14.
 */
@Mapper
@Repository
public interface TransferMemberDAO {
    String TABLE_NAME = "transfer_member";
    String INSERT_FIELDS = "create_time,from_members,to_members,match_item_id,stage";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    TransferMember selectByPrimaryKey(@Param("id") int id);

    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{createTime},#{fromMembers},#{toMembers},#{matchItemId},#{stage})"})
    int insert(TransferMember transferMember);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    List<TransferMember> selectByMatchItemId(@Param("matchItemId")int matchItemId);

    int updateByPrimaryKeySelective(TransferMember transferMember);

    @Delete({"delete from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    int deleteAllByMatchItemId(@Param("matchItemId") int matchItemId);
}
