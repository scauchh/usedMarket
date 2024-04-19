package org.scau.service;

import org.scau.model.pojo.Question;

import java.util.List;

public interface QuestionService {
    // 添加密保问题
    void addQuestion(Integer questionID, String item);

    // 删除密保问题
    void deleteQuestion(Integer questionID);

    // 更新密保问题
    void updateQuestion(Integer oldQuestionID, Integer questionID, String item);

    // 根据ID获取密保问题
    Question getQuestionByID(Integer questionID);

    // 根据内容获取密保问题
    Question getQuestionByItem(String item);

    // 获取所有密保问题
    List<Question> getAllQuestion();
}
