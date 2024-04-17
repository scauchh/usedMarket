package org.scau.controller;

import org.scau.pojo.Question;
import org.scau.pojo.vo.Result;
import org.scau.service.QuestionService;
import org.scau.service.RelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/question")
public class QuestionController {

    final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    RelationService relationService;

    // 检验数据
    private String verifyData(Integer questionID, String item){
        // 检验类别编号
        if(questionID == null){
            return "编号不能为空";
        }

        // 检验类别名称
        if (Objects.equals(item, "null") || item.isEmpty()) {
            return "密保问题不能为空";
        } else if(item.length()>15){
            return "密保问题长度不能超过15位";
        }

        return "success";
    }

    // 添加密保问题
    @RequestMapping("/addQuestion")
    public Result addQuestion(Integer questionID, String item){
        String result = verifyData(questionID, item);
        if(!result.equals("success")) return Result.error(result);

        Question q = questionService.getQuestionByID(questionID);
        if(q != null) return Result.error("密保问题编号已存在");

        q = questionService.getQuestionByItem(item);
        if(q != null) return Result.error("密保问题已存在");

        try {
            questionService.addQuestion(questionID, item);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("添加密保问题失败");
        }
    }

    // 删除密保问题
    @RequestMapping("/deleteQuestion")
    public Result deleteQuestion(Integer questionID){
        Integer num = relationService.getRelationNumByID(questionID);
        if(num!=0) return Result.error("密保问题正在被使用");

        try{
            questionService.deleteQuestion(questionID);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("删除密保问题失败");
        }
    }

    // 修改密保问题
    @RequestMapping("/changeQuestion")
    public Result changeQuestion(Integer oldQuestionID, String oldItem, Integer questionID, String item){
        String result = verifyData(questionID, item);
        if(!result.equals("success")) return Result.error(result);

        Question q = questionService.getQuestionByID(questionID);
        if(q != null && !Objects.equals(q.getQuestionID(), oldQuestionID)) return Result.error("密保问题编号已存在");

        q = questionService.getQuestionByItem(item);
        if(q != null && !Objects.equals(q.getItem(), oldItem)) return Result.error("密保问题已存在");

        try{
            questionService.updateQuestion(oldQuestionID, questionID, item);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("修改密保问题失败");
        }
    }

    // 获取所有密保问题
    @RequestMapping("/getAllQuestion")
    public Result getAllQuestion() {
        try {
            List<Question> questions = questionService.getAllQuestion();
            return Result.success(questions);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取密保问题失败");
        }
    }
}
