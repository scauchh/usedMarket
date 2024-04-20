package org.scau.controller;

import org.scau.model.pojo.Relation;
import org.scau.model.Result;
import org.scau.service.RelationService;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/relation")
public class RelationController {

    final Logger logger = LoggerFactory.getLogger(RelationController.class);

    @Autowired
    RelationService relationService;

    // 添加关系
    private Result addRelation(Integer questionID, Integer userID, String answer) {
        try {
            relationService.addRelation(questionID, userID, answer);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("添加关系失败");
        }
    }

    // 更新关系
    private Result updateRelation(Integer relationID, Integer questionID, String answer){
        try{
            relationService.updateRelation(relationID, questionID, answer);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("更新关系失败");
        }
    }

    // 保存关系
    @RequestMapping("/saveRelation")
    public Result saveRelation(Integer questionID, String answer) {
        if(questionID == -1) return Result.error("请选择一个密保问题");
        if(answer==null|| answer.isEmpty()) return Result.error("回答不能为空");
        if(answer.length()>15) return Result.error("回答的长度不能超过15位");

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = Integer.parseInt(map.get("id").toString());

        Relation relation = relationService.searchRelationByID(userID);

        if(relation==null) return addRelation(questionID, userID, answer);
        else return updateRelation(relation.getRelationID(), questionID, answer);
    }

    // 获取当前用户密保
    @RequestMapping("/getCurrentRelation")
    public Result getCurrentQuestion() {
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userID = (Integer) map.get("id");

            Relation relation = relationService.searchRelationByID(userID);
            if(relation==null) relation = new Relation(null, null, null, "");
            return Result.success(relation);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取当前用户密保失败");
        }
    }
}
