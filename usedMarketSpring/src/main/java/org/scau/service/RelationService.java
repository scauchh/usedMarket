package org.scau.service;

import org.scau.pojo.Relation;

public interface RelationService {
    // 添加新的关系
    void addRelation(Integer questionID, Integer userID, String answer);

    // 根据ID更新关系
    void updateRelation(Integer relationID, Integer questionID, String answer);

    // 根据用户ID查找关系
    Relation searchRelationByID(Integer userID);

    // 根据ID查找密保问题的使用次数
    Integer getRelationNumByID(Integer questionID);
}
