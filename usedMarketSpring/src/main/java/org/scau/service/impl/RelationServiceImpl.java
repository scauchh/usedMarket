package org.scau.service.impl;

import org.scau.mapper.RelationMapper;
import org.scau.pojo.Relation;
import org.scau.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationServiceImpl implements RelationService  {
    @Autowired
    RelationMapper relationMapper;

    // 添加新的关系
    @Override
    public void addRelation(Integer questionID, Integer userID, String answer) {
        relationMapper.addRelation(questionID, userID, answer);
    }

    // 根据ID更新关系
    @Override
    public void updateRelation(Integer relationID, Integer questionID, String answer) {
        relationMapper.updateRelation(relationID, questionID, answer);
    }

    // 根据用户ID查找关系
    @Override
    public Relation searchRelationByID(Integer userID) {
        return relationMapper.searchRelationByID(userID);
    }

    // 根据ID查找密保问题的使用次数
    @Override
    public Integer getRelationNumByID(Integer questionID) {
        return relationMapper.getRelationNumByID(questionID);
    }
}
