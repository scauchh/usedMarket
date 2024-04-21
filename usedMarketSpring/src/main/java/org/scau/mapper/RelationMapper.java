package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.scau.model.pojo.Relation;

@Mapper
public interface RelationMapper {
    // 添加新的关系
    @Insert("INSERT INTO relation(question_id, user_id, answer) VALUES (#{questionID}, #{userID}, #{answer})")
    void addRelation(Integer questionID, Integer userID, String answer);

    // 根据ID更新关系
    @Update("UPDATE relation SET question_id = #{questionID}, answer = #{answer} WHERE user_id = #{userID}")
    void updateRelation(Integer questionID, Integer userID, String answer);

    // 根据用户ID查找关系
    @Select("SELECT * FROM relation WHERE user_id = #{userID}")
    Relation searchRelationByID(Integer userID);

    // 根据ID查找密保问题的使用次数
    @Select("SELECT COUNT(*) FROM relation WHERE question_id = #{questionID}")
    Integer getRelationNumByID(Integer questionID);
}
