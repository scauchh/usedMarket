package org.scau.service;

import org.scau.pojo.Type;

import java.util.List;

public interface TypeService {
    // 添加物品类型
    void addType(Integer typeID, String typeName);

    // 删除物品类型
    void deleteType(String typeName);

    // 修改物品类别
    void updateType(Integer oldTypeID, Integer typeID, String typeName);

    // 获取所有物品类型
    List<Type> getAllType();

    // 根据ID查询类型
    Type getTypeByID(Integer typeID);

    // 根据名称查询类型
    Type getTypeByName(String typeName);
}
