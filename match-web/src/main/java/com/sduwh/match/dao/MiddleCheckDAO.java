package com.sduwh.match.dao;

import com.sduwh.match.model.entity.MiddleCheck;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by qxg on 17-9-11.
 */
@Mapper
@Repository
public interface MiddleCheckDAO {
    String TABLE_NAME = "middle_check";
    String INSERT_FIELDS = "comu_with_teacher,project_result,is_expect,cost_situation,point_by_student,point_by_teacher,follow_plan,follow_point,view_of_teacher,level_by_teacher,view_of_academy,view_of_super,match_item_id,stage";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME," (",INSERT_FIELDS,") values(#{comuWithTeacher},#{projectResult},#{isExpect},#{costSituation},#{pointByStudent},#{pointByTeacher},#{followPlan},#{followPoint},#{viewOfTeacher},#{levelByTeacher},#{viewOfAcademy},#{viewOfSuper},#{matchItemId},#{stage})"})
    int insert(MiddleCheck middleCheck);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    MiddleCheck selectByPrimaryKey(@Param("id") Integer id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where match_item_id = #{id}"})
    MiddleCheck selectByMatchItemId(@Param("id") Integer matchItemId);


    @Update({"update",TABLE_NAME,"set comu_with_teacher = #{comuWithTeacher}," +
            "project_result = #{projectResult}," +
            "is_expect = #{isExpect}," +
            "cost_situation = #{costSituation}," +
            "point_by_student = #{pointByStudent}," +
            "point_by_teacher = #{pointByTeacher}," +
            "follow_plan = #{followPlan}," +
            "follow_point = #{followPoint}," +
            "view_of_teacher = #{viewOfTeacher}," +
            "level_by_teacher = #{levelByTeacher}," +
            "view_of_academy = #{viewOfAcademy}," +
            "view_of_super = #{viewOfSuper}," +
            "match_item_id = #{matchItemId}," +
            "stage = #{stage} where match_item_id = #{matchItemId}"})
    int updateByMatchItemId(MiddleCheck middleCheck);

    int updateByMatchItemIdSelective(MiddleCheck middleCheck);

    @Delete({"delete from",TABLE_NAME,"where match_item_id = #{matchItemId}"})
    int deleteAllByMatchItemId(@Param("matchItemId") int matchItemId);

}
