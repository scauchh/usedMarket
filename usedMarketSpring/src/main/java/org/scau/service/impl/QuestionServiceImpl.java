package org.scau.service.impl;

import org.scau.mapper.QuestionMapper;
import org.scau.model.pojo.Question;
import org.scau.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    // 添加密保问题
    @Override
    public void addQuestion(Integer questionID, String item) {
        questionMapper.addQuestion(questionID, item);
    }

    // 删除密保问题
    @Override
    public void deleteQuestion(Integer questionID) {
        questionMapper.deleteQuestion(questionID);
    }

    // 更新密保问题
    @Override
    public void updateQuestion(Integer oldQuestionID, Integer questionID, String item) {
        questionMapper.updateQuestion(oldQuestionID, questionID, item);
    }

    // 根据ID获取密保问题
    @Override
    public Question getQuestionByID(Integer questionID) {
        return questionMapper.getQuestionByID(questionID);
    }

    // 根据内容获取密保问题
    @Override
    public Question getQuestionByItem(String item) {
        return questionMapper.getQuestionByItem(item);
    }

    // 获取所有密保问题
    @Override
    public List<Question> getAllQuestion() {
        return questionMapper.getAllQuestion();
    }
}
