package com.sduwh.match.dao;

import com.sduwh.match.model.entity.ConcludingStatement;
import com.sduwh.match.model.entity.MiddleCheck;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by qxg on 17-9-11.
 */
@Mapper
@Repository
public interface ConcludingStatementDAO {
    String TABLE_NAME = "concluding_statement";
    String INSERT_FIELDS = "completion_situation,research_result,self_judge,price_situation,view_of_teacher,view_of_academy,view_of_super,match_item_id,up_pos,stage";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{completionSituation},#{researchResult},#{selfJudge},#{priceSituation},#{viewOfTeacher},#{viewOfAcademy},#{viewOfSuper},#{matchItemId},#{upPos},#{stage})"})
    int insert(ConcludingStatement concludingStatement);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    ConcludingStatement selectByPrimaryKey(@Param("id") Integer id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{id}"})
    ConcludingStatement selectByMatchItemId(@Param("id") Integer matchItemId);


    @Update({"update",TABLE_NAME,"set completion_situation = #{completionSituation}," +
            "research_result = #{researchResult}," +
            "self_judge = #{selfJudge}," +
            "price_situation = #{priceSituation}," +
            "view_of_teacher = #{viewOfTeacher}," +
            "view_of_academy = #{viewOfAcademy}," +
            "view_of_super = #{viewOfSuper}," +
            "up_pos = #{upPos}," +
            "match_item_id = #{matchItemId}," +
            "stage = #{stage} where match_item_id = #{matchItemId}"})
    int updateByMatchItemId(ConcludingStatement concludingStatement);

    int updateByMatchItemIdSelective(ConcludingStatement concludingStatement);

}
