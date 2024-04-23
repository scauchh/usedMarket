package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.model.pojo.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {
    // 添加密保问题
    @Insert("INSERT INTO question VALUES (#{questionID}, #{item})")
    void addQuestion(Integer questionID, String item);

    // 根据密保问题编号删除密保问题
    @Delete("DELETE FROM question WHERE question_id = #{questionID}")
    void deleteQuestion(Integer questionID);

    // 更新密保问题
    @Update("UPDATE question SET question_id = #{questionID}, item = #{item} WHERE question_id = #{oldQuestionID}")
    void updateQuestion(Integer oldQuestionID, Integer questionID, String item);

    // 根据密保问题编号获取密保问题
    @Select("SELECT * FROM question WHERE question_id = #{questionID}")
    Question getQuestionByID(Integer questionID);

    // 根据密保问题内容获取密保问题
    @Select("SELECT * FROM question WHERE item = #{item}")
    Question getQuestionByItem(String item);

    // 获取所有密保问题
    @Select("SELECT * FROM question ORDER BY question_id")
    List<Question> getAllQuestion();
}
